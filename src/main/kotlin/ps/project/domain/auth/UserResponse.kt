package ps.project.domain.auth

data class UserResponse (
    val message: String,
    val userId: Int,
    val email: String,
    val name: String,
    val cienciaID: String
)