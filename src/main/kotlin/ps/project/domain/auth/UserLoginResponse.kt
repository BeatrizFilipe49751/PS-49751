package ps.project.domain.auth

import java.time.LocalDateTime

data class UserLoginResponse(
    val message: String,
    val id: Int,
    val email: String,
    val token: String,
    val issuedAt: LocalDateTime,
    val expiresAt: LocalDateTime
)
