package ps.project.utils

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import ps.project.domain.User
import ps.project.domain.auth.*
import ps.project.repository.UserRepository
import ps.project.repository.UserTokenRepository
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

object TestUtils {
    private val random = Random()

    fun createNewClient(port: Int): WebTestClient =
        WebTestClient.bindToServer()
            .baseUrl("http://localhost:$port")
            .responseTimeout(Duration.ofSeconds(5))
            .build()

    fun resetDatabase(
        userRepository: UserRepository,
        userTokenRepository: UserTokenRepository,
    ) {
        userTokenRepository.deleteAll()
        userRepository.deleteAll()
    }

    private fun randomEmail(): String = "testuser${random.nextInt(100000)}@example.com"

    private fun randomCienciaID(): String = "ciencia-${random.nextInt(100000)}"

    private fun randomPassword(): String = "Pass1234!"

    private fun randomName(): String = "User${random.nextInt(100000)}"

    fun randomUserDTO(): UserDTO = UserDTO(
        email = randomEmail(),
        password = randomPassword(),
        cienciaID = randomCienciaID()
    )

    fun createUser(
        userDTO: UserDTO,
        userRepository: UserRepository,
        passwordEncoder: BCryptPasswordEncoder
    ): User {
        val encodedPassword = passwordEncoder.encode(userDTO.password)
        val user = User(
            name = randomName(),
            email = userDTO.email,
            password = encodedPassword,
            cienciaID = userDTO.cienciaID
        )
        return userRepository.save(user)
    }

    fun loginUserAndGetToken(loginDTO: UserLoginDTO, client: WebTestClient): UserToken {
        val result = client.post().uri("/api/users/login")
            .bodyValue(loginDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody<UserLoginResponse>()
            .returnResult()

        val body = result.responseBody ?: throw RuntimeException("Login response body null")

        // Map UserLoginResponse to UserToken manually:
        return UserToken(
            id = body.id,
            user = User(email = body.email, name = "", password = "", cienciaID = ""),
            token = body.token,
            issuedAt = body.issuedAt,
            expiresAt = body.expiresAt
        )
    }

    fun createMockWebServer(responseXml: String, port: Int = 3000): MockWebServer {
        val server = MockWebServer()
        server.start(port)
        server.enqueue(
            MockResponse()
                .setBody(responseXml)
                .setHeader("Content-Type", "application/xml")
                .setResponseCode(200)
        )
        return server
    }
}
