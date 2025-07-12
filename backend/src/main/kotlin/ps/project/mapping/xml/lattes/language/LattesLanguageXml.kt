package ps.project.mapping.xml.lattes.language

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.domain.language.LanguageDTO
import ps.project.domain.language.LanguageLevel

data class LattesLanguageXml(
    @JacksonXmlProperty(isAttribute = true, localName = "DESCRICAO-DO-IDIOMA")
    val description: String,

    @JacksonXmlProperty(isAttribute = true, localName = "PROFICIENCIA-DE-COMPREENSAO")
    val comprehension: String?,

    @JacksonXmlProperty(isAttribute = true, localName = "PROFICIENCIA-DE-LEITURA")
    val reading: String?,

    @JacksonXmlProperty(isAttribute = true, localName = "PROFICIENCIA-DE-FALA")
    val speaking: String?,

    @JacksonXmlProperty(isAttribute = true, localName = "PROFICIENCIA-DE-ESCRITA")
    val writing: String?
) {
    fun toDTO() = LanguageDTO(
        language = description,
        comprehension = mapProficiency(comprehension),
        reading = mapProficiency(reading),
        speaking = mapProficiency(speaking),
        writing = mapProficiency(writing)
    )

    private fun mapProficiency(level: String?): LanguageLevel? {
        return when (level?.uppercase()) {
            "POUCO" -> LanguageLevel.Beginner
            "RAZOAVELMENTE" -> LanguageLevel.Intermediate
            "BEM" -> LanguageLevel.Advanced
            else -> null
        }
    }
}
