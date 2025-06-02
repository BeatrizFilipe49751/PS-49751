package ps.project.domain.education

data class SupervisorDTO(
    val id: Int? = null,
    val name: String,
    val role: SupervisorRole
) {
    fun toEntity(thesis: Thesis) = Supervisor (
        thesis = thesis,
        name = name,
        role = role
    )
}
