package ps.project.mapping.xml.cienciaVitae.contacts

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import ps.project.mapping.xml.cienciaVitae.common.CienciaVitaeXmlPayload
import ps.project.mapping.xml.cienciaVitae.common.CodeValueXml

@JacksonXmlRootElement(localName = "phone-number", namespace = "http://www.cienciavitae.pt/ns/phone-number")
data class PhoneXml(
    val usageType: CodeValueXml,
    val phoneType: CodeValueXml,

    @field:JacksonXmlProperty(localName = "country-code")
    val countryCode: Int? = null,

    @field:JacksonXmlProperty(localName = "local-number")
    val number: Int
) : CienciaVitaeXmlPayload()
