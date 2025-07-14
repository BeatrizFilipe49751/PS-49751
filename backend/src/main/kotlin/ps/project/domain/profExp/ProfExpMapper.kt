package ps.project.domain.profExp

import ps.project.domain.User

object ProfExpMapper {
    /* ------------  DTO ➜ Entity  ------------ */
    fun toEntity(dto: ProfessionalExperienceDTO, user: User): ProfessionalExperience? {
        val institution = dto.institution
        val description = dto.description
        val startDate = dto.startDate
        val endDate = dto.endDate

        return when (dto.type) {
            ProfExpType.Science -> Science(user, institution, description, startDate, endDate)
            ProfExpType.TeachingHE -> TeachingHE(user, institution, description, startDate, endDate)
            ProfExpType.Positions -> Positions(user, institution, description, startDate, endDate)
            ProfExpType.Others -> Others(user, institution, description, startDate, endDate)
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
            type = type,
            institution = profExp.institution,
            description = profExp.description,
            startDate = profExp.startDate,
            endDate = profExp.endDate
        )
    }
}