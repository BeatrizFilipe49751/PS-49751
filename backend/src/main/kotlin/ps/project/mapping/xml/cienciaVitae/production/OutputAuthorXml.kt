package ps.project.mapping.xml.cienciaVitae.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class OutputAuthorXml(
    @JacksonXmlProperty(localName = "author")
    val name: String
)
