package ps.project.domain.language

import ps.project.domain.User

data class LanguageDTO(
    val id: Int? = null,
    val language: String,
    val comprehension: LanguageLevel?,
    val reading: LanguageLevel?,
    val speaking: LanguageLevel?,
    val writing: LanguageLevel?
) {
    fun toEntity(user: User) = Language (
        user = user,
        language = language,
        comprehension = comprehension?.name,
        reading = reading?.name,
        speaking = speaking?.name,
        writing = writing?.name
    )
}
