package ps.project.domain.identifier

import ps.project.domain.Cv

data class IdentifierDTO(
    val id: String,
    val type: IdentifierType
) {
    fun toEntity(cv: Cv) = Identifier (
        cv = cv,
        id = id,
        type = type
    )
}
