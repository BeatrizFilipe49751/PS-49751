package ps.project.mapping.xml.cienciaVitae

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import ps.project.mapping.xml.cienciaVitae.common.CienciaVitaeXmlPayload
import ps.project.mapping.xml.cienciaVitae.common.CodeValueXml

@JacksonXmlRootElement(localName = "author-identifier", namespace = "http://www.cienciavitae.pt/ns/author-identifier")
data class IdentifierXml(
    val identifierType: CodeValueXml,

    @field:JacksonXmlProperty(localName = "identifier")
    val identifier: String
) : CienciaVitaeXmlPayload()
