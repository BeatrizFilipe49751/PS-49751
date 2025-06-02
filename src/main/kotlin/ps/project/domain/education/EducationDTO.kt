package ps.project.domain.education

import ps.project.domain.Cv
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
    fun toEntity(cv: Cv) : Education {
        return Education(
            cv = cv,
            degree = degree,
            course = course,
            institution = institution,
            classification = classification,
            status = status,
            courseCode = courseCode,
            startDate = startDate,
            endDate = endDate,
        )
    }
}
