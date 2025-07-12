package ps.project.domain.identifier

import ps.project.domain.User

data class IdentifierDTO(
    val id: String,
    val type: IdentifierType
) {
    fun toEntity(user: User) = Identifier (
        user = user,
        id = id,
        type = type
    )
}
