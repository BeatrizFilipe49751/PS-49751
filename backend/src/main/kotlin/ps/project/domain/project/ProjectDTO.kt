package ps.project.domain.project

import ps.project.domain.User
import java.time.LocalDate

data class ProjectDTO(
    val id: Int? = null,
    val title: String,
    val institution: String?,
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val description: String?,
    val fundingType: FundingType,
    val identifier: String?,
    val role: String? = null,
    val state: ProjectState?,
    val authors: List<String>
) {
    fun toEntity(user: User) = Project (
        id = id ?: 0,
        user = user,
        title = title,
        institution = institution,
        startDate = startDate,
        endDate = endDate,
        description = description,
        fundingType = fundingType.name,
        identifier = identifier,
        role = role,
        state = state?.name
    )
}
