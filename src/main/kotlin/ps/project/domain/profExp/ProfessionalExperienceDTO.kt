package ps.project.domain.profExp

import ps.project.domain.Cv
import java.time.LocalDate

data class ProfessionalExperienceDTO(
    val id: Int? = null,
    val institution: String,
    val role: String,
    val description: String? = null,
    val startDate: LocalDate,
    val endDate: LocalDate? = null
) {
    fun toEntity(cv: Cv) = ProfessionalExperience (
        cv = cv,
        institution = institution,
        role = role,
        description = description,
        startDate = startDate,
        endDate = endDate
    )
}