package ps.project.mapping.xml.lattes

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class TeamMember(
    @JacksonXmlProperty(isAttribute = true, localName = "NOME-PARA-CITACAO")
    val citationName: String? = null
)