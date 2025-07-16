package ps.project.domain.contact

import ps.project.domain.User

data class EmailDTO(
    val id: Int? = null,
    val type: ContactType,
    val address: String
) {
    fun toEntity(user: User) = Email (
        id = id ?: 0,
        user = user,
        type = type.name,
        address = address
    )
}
