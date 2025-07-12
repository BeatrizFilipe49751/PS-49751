package ps.project.domain.author

import ps.project.domain.User

data class AuthorDTO(
    val id: Int? = null,
    val citationName: String
) {
    fun toEntity(user: User) = Author (
        user = user,
        citationName = citationName
    )
}
