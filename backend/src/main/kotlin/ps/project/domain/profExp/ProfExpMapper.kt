package ps.project.domain.profExp

import ps.project.domain.User

object ProfExpMapper {
    /* ------------  DTO ➜ Entity  ------------ */
    fun toEntity(dto: ProfessionalExperienceDTO, user: User): ProfessionalExperience? {
        val id = dto.id ?: 0
        val institution = dto.institution
        val description = dto.description
        val startDate = dto.startDate
        val endDate = dto.endDate

        return when (dto.type) {
            ProfExpType.Science -> Science(id, user, institution, description, startDate, endDate)
            ProfExpType.TeachingHE -> TeachingHE(id, user, institution, description, startDate, endDate)
            ProfExpType.Positions -> Positions(id, user, institution, description, startDate, endDate)
            ProfExpType.Others -> Others(id, user, institution, description, startDate, endDate)
        }
    }

    /* ------------  Entity ➜ DTO  ------------ */
    fun toDTO(profExp: ProfessionalExperience): ProfessionalExperienceDTO? {
        val type = when (profExp) {
            is Science -> ProfExpType.Science
            is TeachingHE -> ProfExpType.TeachingHE
            is Positions -> ProfExpType.Positions
            is Others -> ProfExpType.Others
            else -> return null
        }
        return ProfessionalExperienceDTO(
            id = profExp.id,
            type = type,
            institution = profExp.institution,
            description = profExp.description,
            startDate = profExp.startDate,
            endDate = profExp.endDate
        )
    }
}