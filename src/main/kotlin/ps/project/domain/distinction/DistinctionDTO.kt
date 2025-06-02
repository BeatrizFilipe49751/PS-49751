package ps.project.domain.distinction

import ps.project.domain.Cv

data class DistinctionDTO(
    val id: Int? = null,
    val type: DistinctionType,
    val name: String,
    val year: Int,
    val promotingEntity: String?
) {
    fun toEntity(cv: Cv) = Distinction (
        cv = cv,
        type = type,
        name = name,
        year = year,
        promotingEntity = promotingEntity
    )
}
