package ps.project.domain.activity

import ps.project.domain.Cv
import java.time.LocalDate

data class ActivityDTO(
    val id: Int? = null,
    val type: String,
    val description: String? = null,
    val startDate: LocalDate,
    val endDate: LocalDate? = null
) {
    fun toEntity(cv: Cv) = Activity (
        cv = cv,
        type = type,
        description = description,
        startDate = startDate,
        endDate = endDate
    )
}
