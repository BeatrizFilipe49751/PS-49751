package ps.project.controllers

import org.springframework.http.HttpStatus
import ps.project.domain.UserDTO
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.*
import ps.project.domain.UserLoginDTO
import ps.project.services.UserService

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody userDTO: UserDTO): ResponseEntity<String> {
        return try {
            userService.registerUser(userDTO)
            ResponseEntity.ok("User registered successfully")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDTO: UserLoginDTO): ResponseEntity<String> {
        return try {
            val token = userService.loginUser(loginDTO)
            ResponseEntity.ok(token)
        } catch (e: BadCredentialsException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.message)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong")
        }
    }

    @PostMapping("/logout")
    fun logout(@RequestHeader("Authorization") authHeader: String): ResponseEntity<String> {
        return try {
            val token = authHeader.replace("Bearer ", "")
            userService.logoutUser(token)
            ResponseEntity.ok("Token deleted successfully")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
        }
    }
}