package ps.project.domain.production

import ps.project.domain.User
import ps.project.domain.education.Education
import ps.project.domain.education.SupervisorDTO
import java.time.LocalDate

data class ThesisDTO(
    val id: Int? = null,
    val title: String,
    val date: LocalDate,
    val supervisors: List<SupervisorDTO> = emptyList()
) {
    fun toEntity(user: User, education: Education): Thesis {
        return Thesis (
            id = id ?: 0,
            title = title,
            date = date,
            education = education,
            user = user
        )
    }
}
