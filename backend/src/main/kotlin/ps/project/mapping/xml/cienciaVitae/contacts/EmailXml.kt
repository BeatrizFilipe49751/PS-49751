package ps.project.mapping.xml.cienciaVitae.contacts

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import ps.project.mapping.xml.cienciaVitae.common.CienciaVitaeXmlPayload
import ps.project.mapping.xml.cienciaVitae.common.CodeValueXml

@JacksonXmlRootElement(localName = "email", namespace = "http://www.cienciavitae.pt/ns/email")
data class EmailXml(
    val emailType: CodeValueXml,

    @field:JacksonXmlProperty(localName = "email-address")
    val email: String
) : CienciaVitaeXmlPayload()
