package ps.project.domain.education

import ps.project.domain.production.Thesis

data class SupervisorDTO(
    val name: String,
    val role: SupervisorRole
) {
    fun toEntity(thesis: Thesis) = Supervisor(
        id = SupervisorId(
            thesisId = thesis.id,
            name = name
        ),
        role = role.name,
        thesis = thesis
    )
}
