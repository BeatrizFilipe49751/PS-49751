package ps.project.domain.education

import ps.project.domain.User
import ps.project.domain.production.ThesisDTO
import java.time.LocalDate

data class EducationDTO(
    val id: Int? = null,
    val degree: EducationDegree,
    val course: String,
    val institution: String,
    val classification: String? = null,
    val status: EducationStatus,
    val courseCode: String?,
    val startDate: LocalDate?,
    val endDate: LocalDate,
    val thesis: ThesisDTO? = null
) {
    fun toEntity(user: User) : Education {
        return Education(
            id = id ?: 0,
            user = user,
            degree = degree.name,
            course = course,
            institution = institution,
            classification = classification,
            status = status.name,
            courseCode = courseCode,
            startDate = startDate,
            endDate = endDate,
        )
    }
}
