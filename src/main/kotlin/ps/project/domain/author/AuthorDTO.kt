package ps.project.domain.author

import ps.project.domain.Cv

data class AuthorDTO(
    val id: Int? = null,
    val citationName: String
) {
    fun toEntity(cv: Cv?) = Author (
        cv = cv,
        citationName = citationName
    )
}
