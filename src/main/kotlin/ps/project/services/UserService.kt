package ps.project.services

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ps.project.domain.*
import ps.project.repository.UserRepository
import ps.project.repository.UserTokenRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userTokenRepository: UserTokenRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {

    fun registerUser(userDTO: UserDTO): User {
        if (userRepository.findUserByEmail(userDTO.email).isPresent) {
            throw Exception("User already exists") // TODO: Improve exception
        }
        val password = passwordEncoder.encode(userDTO.password)
        val birthdate = LocalDate.parse(userDTO.birthdate)
        val gender = userDTO.gender.uppercase()

        val user = User(
            name = userDTO.name,
            email = userDTO.email,
            password = password,
            birthdate = birthdate,
            gender = gender,
            cienciaID = userDTO.cienciaID
        )
        return userRepository.save(user)
    }

    fun loginUser(userLoginDTO: UserLoginDTO): String {
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

    private fun generateToken(user: User): String {
        val token = UUID.randomUUID().toString()
        val issuedAt = LocalDateTime.now()
        val expiresAt = issuedAt.plusDays(1) // 24 hours

        val userToken = UserToken(
            user = user,
            token = token,
            issuedAt = issuedAt,
            expiresAt = expiresAt
        )

        userTokenRepository.save(userToken)

        return token
    }

    fun validateToken(token: String): User? {
        val userToken = userTokenRepository.findByToken(token)
            .orElseThrow { BadCredentialsException("Invalid or expired token") }

        if (userToken.expiresAt.isBefore(LocalDateTime.now())) {
            userTokenRepository.delete(userToken)
            throw BadCredentialsException("Token expired")
        }

        return userToken.user
    }

    fun findUserByEmail(email: String): User? {
        return userRepository.findUserByEmail(email).orElse(null)
    }
}