package ps.project.domain.activity

import ps.project.domain.education.SupervisorRole
import java.time.LocalDate

data class ActivityDTO(
    val id: Int? = null,
    val type: ActivityType,
    val title: String,
    val date: LocalDate,
    val student: String? = null,
    val institution: String? = null,
    val course: String? = null,
    val courseCode: String? = null,
    val endDate: LocalDate? = null,
    val role: SupervisorRole? = null,
    val degree: DegreeType? = null,
)
