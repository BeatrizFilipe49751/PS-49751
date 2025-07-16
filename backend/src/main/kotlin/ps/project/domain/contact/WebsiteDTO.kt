package ps.project.domain.contact

import ps.project.domain.User

data class WebsiteDTO(
    val id: Int? = null,
    val type: WebsiteType,
    val url: String
) {
    fun toEntity(user: User) = Website (
        id = id ?: 0,
        user = user,
        type = type.name,
        url = url
    )
}
