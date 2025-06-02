package ps.project.controllers

import okhttp3.mockwebserver.MockWebServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.web.reactive.server.WebTestClient
import ps.project.domain.auth.UserLoginDTO
import ps.project.repository.UserRepository
import ps.project.repository.UserTokenRepository
import ps.project.utils.TestUtils
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["spring.profiles.active=test"]
)
@AutoConfigureWebTestClient
class UserControllerTest {

    @LocalServerPort
    private var port: Int = 0

    private lateinit var client: WebTestClient

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userTokenRepository: UserTokenRepository

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    private lateinit var mockWebServer: MockWebServer

    @BeforeTest
    fun resetInit() {
        client = TestUtils.createNewClient(port)
        TestUtils.resetDatabase(userRepository, userTokenRepository)
        val mockXml = """
            <person-info:person-info xmlns:person-info="http://example.org">
                <person-info:full-name>João da Silva</person-info:full-name>
            </person-info:person-info>
        """.trimIndent()
        mockWebServer = TestUtils.createMockWebServer(mockXml)
    }

    @AfterTest
    fun tearDown() {
        TestUtils.resetDatabase(userRepository, userTokenRepository)
        mockWebServer.shutdown()
    }

    @Test
    fun `Register User Successfully`() {
        val userDTO = TestUtils.randomUserDTO()
        val email = userDTO.email
        val cienciaId = userDTO.cienciaID

        client.post()
            .uri("/api/users/register")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(userDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo("João da Silva")
            .jsonPath("$.email").isEqualTo(email)
            .jsonPath("$.cienciaID").isEqualTo(cienciaId)
    }

    @Test
    fun `Register User Already Exists Returns Bad Request`() {
        val userDTO = TestUtils.randomUserDTO()
        // Create first user manually
        TestUtils.createUser(userDTO, userRepository, passwordEncoder)

        client.post().uri("/api/users/register")
            .bodyValue(userDTO)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .consumeWith { res ->
                assert(res.responseBody?.contains("User already exists") == true)
            }
    }

    @Test
    fun `Login User Successfully`() {
        val userDTO = TestUtils.randomUserDTO()
        TestUtils.createUser(userDTO, userRepository, passwordEncoder)

        val loginDTO = UserLoginDTO(email = userDTO.email, password = userDTO.password)

        client.post().uri("/api/users/login")
            .bodyValue(loginDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("message").isEqualTo("User logged in successfully. Token issued.")
            .jsonPath("email").isEqualTo(userDTO.email)
            .jsonPath("token").exists()
            .jsonPath("issuedAt").exists()
            .jsonPath("expiresAt").exists()
    }

    @Test
    fun `Login User With Invalid Password Returns Unauthorized`() {
        val userDTO = TestUtils.randomUserDTO()
        TestUtils.createUser(userDTO, userRepository, passwordEncoder)

        val loginDTO = UserLoginDTO(email = userDTO.email, password = "wrongpassword")

        client.post().uri("/api/users/login")
            .bodyValue(loginDTO)
            .exchange()
            .expectStatus().isUnauthorized
            .expectBody(String::class.java)
            .consumeWith { res ->
                assert(res.responseBody?.contains("Invalid password") == true)
            }
    }

    @Test
    fun `Logout User Successfully`() {
        val userDTO = TestUtils.randomUserDTO()
        TestUtils.createUser(userDTO, userRepository, passwordEncoder)

        val loginDTO = UserLoginDTO(email = userDTO.email, password = userDTO.password)
        val userToken = TestUtils.loginUserAndGetToken(loginDTO, client)

        client.post().uri("/api/users/logout")
            .header("Authorization", "Bearer ${userToken.token}")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("message").isEqualTo("User logged out successfully. Token deleted.")
            .jsonPath("token").isEqualTo(userToken.token)

        // Confirm token deleted from DB
        val tokenOpt = userTokenRepository.findByToken(userToken.token)
        assert(tokenOpt.isEmpty)
    }

    @Test
    fun `Logout Without Token Returns Unauthorized`() {
        client.post().uri("/api/users/logout")
            .exchange()
            .expectStatus().isForbidden
            .expectBody(String::class.java)
            .consumeWith { res ->
                assert(res.responseBody?.contains("Forbidden") == true)
            }
    }

}
