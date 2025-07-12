package ps.project.domain.contact

import ps.project.domain.User

data class WebsiteDTO(
    val id: Int? = null,
    val type: WebsiteType,
    val url: String
) {
    fun toEntity(user: User) = Website (
        user = user,
        type = type,
        url = url
    )
}
