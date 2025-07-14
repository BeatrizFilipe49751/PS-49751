package ps.project.domain.profExp

import ps.project.domain.activity.ActivityDTO
import java.time.LocalDate

data class ProfessionalExperienceDTO(
    val id: Int? = null,
    val institution: String,
    val description: String? = null,
    val startDate: LocalDate,
    val endDate: LocalDate? = null,
    val type: ProfExpType,
    val activities: List<ActivityDTO>? = null,
)