package ps.project.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.*
import ps.project.domain.auth.*
import ps.project.services.UserService

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody userDTO: UserDTO): ResponseEntity<Any> {
        return try {
            val user = userService.registerUser(userDTO)
            val response = UserResponse(
                message = "User registered successfully",
                userId = user.id,
                email = user.email,
                name = user.name,
                cienciaID = user.cienciaID
            )
            ResponseEntity.ok(response)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDTO: UserLoginDTO): ResponseEntity<Any> {
        return try {
            val userToken = userService.loginUser(loginDTO)
            val response = UserLoginResponse(
                message = "User logged in successfully. Token issued.",
                id = userToken.id,
                email = userToken.user.email,
                token = userToken.token,
                issuedAt = userToken.issuedAt,
                expiresAt = userToken.expiresAt
            )
            ResponseEntity.ok(response)
        } catch (e: BadCredentialsException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.message)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong")
        }
    }

    @PostMapping("/logout")
    fun logout(@RequestHeader("Authorization") authHeader: String): ResponseEntity<Any> {
        return try {
            val token = authHeader.replace("Bearer ", "")
            userService.logoutUser(token)
            val response = UserLogoutResponse(
                message = "User logged out successfully. Token deleted.",
                token = token
            )
            ResponseEntity.ok(response)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
        }
    }

}