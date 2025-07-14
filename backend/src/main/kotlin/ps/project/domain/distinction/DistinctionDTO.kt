package ps.project.domain.distinction

import ps.project.domain.User

data class DistinctionDTO(
    val id: Int? = null,
    val type: DistinctionType,
    val name: String,
    val year: Int,
    val promotingEntity: String?
) {
    fun toEntity(user: User) = Distinction (
        user = user,
        type = type.name,
        name = name,
        year = year,
        promotingEntity = promotingEntity
    )
}
