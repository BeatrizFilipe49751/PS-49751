package ps.project.domain

data class CvDTO(
    val id: Int,
    val userId: Int,
    val summary: String,
    val language: List<LanguageDTO>
    //val education: List<EducationDTO>
)
