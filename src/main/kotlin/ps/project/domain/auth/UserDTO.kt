package ps.project.domain.auth

data class UserDTO (
    val id: Int? = null,
    val email: String,
    val password: String,
    val cienciaID: String
)
