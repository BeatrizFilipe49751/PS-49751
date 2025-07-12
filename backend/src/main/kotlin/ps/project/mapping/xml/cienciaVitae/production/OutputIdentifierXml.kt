package ps.project.mapping.xml.cienciaVitae.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import ps.project.mapping.xml.cienciaVitae.common.CodeValueXml

@JacksonXmlRootElement(localName = "identifier")
data class OutputIdentifierXml(
    @field:JacksonXmlProperty(localName = "identifier")
    val identifier: String,

    @field:JacksonXmlProperty(localName = "identifier-type")
    val identifierType: CodeValueXml,

    @field:JacksonXmlProperty(localName = "relationship-type")
    val relationshipType: CodeValueXml = CodeValueXml("P", "Self")
)
