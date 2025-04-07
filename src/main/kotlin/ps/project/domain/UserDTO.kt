package ps.project.domain

data class UserDTO (
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val birthdate: String,
    val gender: String,
    val cienciaID: String
)
