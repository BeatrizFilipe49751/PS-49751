package ps.project.mapping.xml.cienciaVitae.common

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class InstitutionXml(
    @field:JacksonXmlProperty(localName = "institution-name")
    val name: String
)
