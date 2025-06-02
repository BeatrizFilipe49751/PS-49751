package ps.project.services

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClient
import ps.project.domain.auth.User
import ps.project.domain.auth.UserDTO
import ps.project.domain.auth.UserLoginDTO
import ps.project.domain.auth.UserToken
import ps.project.repository.UserRepository
import ps.project.repository.UserTokenRepository
import java.io.ByteArrayInputStream
import java.time.LocalDateTime
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userTokenRepository: UserTokenRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val webClient: WebClient
) {

    fun registerUser(userDTO: UserDTO): User {
        if (userRepository.findUserByEmail(userDTO.email).isPresent) {
            throw Exception("User already exists")
        }
        val password = passwordEncoder.encode(userDTO.password)
        val fullName = fetchFullNameFromCienciaVitae(userDTO.cienciaID)
        val user = User(name = fullName, email = userDTO.email, password = password, cienciaID = userDTO.cienciaID)
        return userRepository.save(user)
    }

    fun loginUser(userLoginDTO: UserLoginDTO): UserToken {
        val user = userRepository.findUserByEmail(userLoginDTO.email)
            .orElseThrow { BadCredentialsException("Invalid email") }

        if (!passwordEncoder.matches(userLoginDTO.password, user.password)) {
            throw BadCredentialsException("Invalid password")
        }

        return generateToken(user)
    }

    @Transactional
    fun logoutUser(token: String) {
        userTokenRepository.deleteByToken(token)
    }

    private fun generateToken(user: User): UserToken {
        val token = UUID.randomUUID().toString()
        val issuedAt = LocalDateTime.now()
        val expiresAt = issuedAt.plusDays(1)
        val userToken = UserToken(user = user, token = token, issuedAt = issuedAt, expiresAt = expiresAt)
        userTokenRepository.save(userToken)
        return userToken
    }

    private fun fetchFullNameFromCienciaVitae(cienciaID: String): String {
        val url = "http://localhost:8080/api/v1.1/curriculum/$cienciaID/person-info"

        val responseXml = webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(String::class.java)
            .block() ?: throw Exception("Empty response from CiênciaVitae")

        val document = DocumentBuilderFactory.newInstance()
            .newDocumentBuilder()
            .parse(ByteArrayInputStream(responseXml.toByteArray(Charsets.UTF_8)))

        val nodeList = document.getElementsByTagName("person-info:full-name")
        return if (nodeList.length > 0) {
            nodeList.item(0).textContent
        } else {
            throw Exception("Nome não encontrado na resposta da CiênciaVitae.")
        }
    }
}