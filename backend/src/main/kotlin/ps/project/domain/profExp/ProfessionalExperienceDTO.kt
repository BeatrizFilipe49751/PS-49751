package ps.project.domain.profExp

import ps.project.domain.User
import java.time.LocalDate

data class ProfessionalExperienceDTO(
    val id: Int? = null,
    val institution: String,
    val description: String? = null,
    val startDate: LocalDate,
    val endDate: LocalDate? = null
) {
    fun toEntity(user: User) = ProfessionalExperience (
        user = user,
        institution = institution,
        description = description,
        startDate = startDate,
        endDate = endDate
    )
}