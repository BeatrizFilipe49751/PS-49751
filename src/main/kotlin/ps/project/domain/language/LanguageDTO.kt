package ps.project.domain.language

import ps.project.domain.Cv

data class LanguageDTO(
    val id: Int? = null,
    val language: String,
    val comprehension: LanguageLevel?,
    val reading: LanguageLevel?,
    val speaking: LanguageLevel?,
    val writing: LanguageLevel?
) {
    fun toEntity(cv: Cv) = Language (
        cv = cv,
        language = language,
        comprehension = comprehension,
        reading = reading,
        speaking = speaking,
        writing = writing
    )
}
