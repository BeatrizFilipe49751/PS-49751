package ps.project.mapping.xml.cienciaVitae

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import ps.project.mapping.xml.cienciaVitae.common.CienciaVitaeXmlPayload
import ps.project.mapping.xml.cienciaVitae.common.CodeValueXml

@JacksonXmlRootElement(localName = "language-competency", namespace = "http://www.cienciavitae.pt/ns/language-competency")
data class LanguageXml(
    val language: CodeValueXml,

    val read: CodeValueXml? = null,
    val write: CodeValueXml? = null,
    val speak: CodeValueXml? = null,

    @field:JacksonXmlProperty(localName = "understandSpoken")
    val understandSpoken: CodeValueXml? = null
) : CienciaVitaeXmlPayload()
