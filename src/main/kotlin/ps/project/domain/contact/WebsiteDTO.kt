package ps.project.domain.contact

import ps.project.domain.Cv

data class WebsiteDTO(
    val id: Int? = null,
    val type: WebsiteType,
    val url: String
) {
    fun toEntity(cv: Cv) = Website (
        cv = cv,
        type = type,
        url = url
    )
}
