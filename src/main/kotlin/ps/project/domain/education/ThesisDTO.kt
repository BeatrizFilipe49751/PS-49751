package ps.project.domain.education

data class ThesisDTO(
    val id: Int? = null,
    val title: String,
    val supervisors: List<SupervisorDTO> = emptyList()
) {
    fun toEntity(education: Education): Thesis {
        return Thesis (
            education = education,
            title = title,
        )
    }
}
