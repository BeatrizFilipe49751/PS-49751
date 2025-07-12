package ps.project.services

import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.web.reactive.server.WebTestClient
import ps.project.domain.auth.UserLoginDTO
import ps.project.repository.UserRepository
import ps.project.repository.UserTokenRepository
import ps.project.utils.TestUtils
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["spring.profiles.active=test"]
)
@AutoConfigureWebTestClient
class UserServiceTest {
    @LocalServerPort
    private var port: Int = 0

    private lateinit var client: WebTestClient

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userService: UserService

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
    fun `should register new user`() {
        val userDTO = TestUtils.randomUserDTO()

        val registeredUser = userService.registerUser(userDTO)

        assertNotNull(registeredUser.id)
        assertEquals(userDTO.email, registeredUser.email)
        assertEquals("João da Silva", registeredUser.name)
        assertTrue(passwordEncoder.matches(userDTO.password, registeredUser.password))
    }

    @Test
    fun `should throw when user already exists`() {
        val userDTO = TestUtils.randomUserDTO()
        // First registration succeeds
        userService.registerUser(userDTO)

        // Second registration with same email should throw
        val exception = assertThrows<Exception> {
            userService.registerUser(userDTO)
        }
        assertEquals("User already exists", exception.message)
    }

    @Test
    fun `should login valid user`() {
        val userDTO = TestUtils.randomUserDTO()
        val registeredUser = userService.registerUser(userDTO)

        val loginDTO = UserLoginDTO(email = userDTO.email, password = userDTO.password)

        val userToken = userService.loginUser(loginDTO)

        assertNotNull(userToken.token)
        assertEquals(registeredUser.id, userToken.user.id)
        assertTrue(userToken.expiresAt.isAfter(userToken.issuedAt))
    }

    @Test
    fun `should throw on invalid email login`() {
        val loginDTO = UserLoginDTO(email = "nonexistent@example.com", password = "anyPassword")

        val exception = assertThrows<BadCredentialsException> {
            userService.loginUser(loginDTO)
        }
        assertEquals("Invalid email", exception.message)
    }

    @Test
    fun `should throw on invalid password`() {
        val userDTO = TestUtils.randomUserDTO()
        userService.registerUser(userDTO)

        val loginDTO = UserLoginDTO(email = userDTO.email, password = "wrongPassword")

        val exception = assertThrows<BadCredentialsException> {
            userService.loginUser(loginDTO)
        }
        assertEquals("Invalid password", exception.message)
    }

    @Test
    fun `should logout user`() {
        val userDTO = TestUtils.randomUserDTO()
        userService.registerUser(userDTO)

        val loginDTO = UserLoginDTO(email = userDTO.email, password = userDTO.password)
        val userToken = userService.loginUser(loginDTO)

        userService.logoutUser(userToken.token)

        val tokenExists = userTokenRepository.findByToken(userToken.token).isPresent
        assertFalse(tokenExists)
    }

}