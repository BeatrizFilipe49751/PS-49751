package ps.project.domain

data class LanguageDTO(
    val id: Int,
    val language: String,
    val comprehension: Int,
    val reading: Int,
    val speaking: Int,
    val writing: Int
)
