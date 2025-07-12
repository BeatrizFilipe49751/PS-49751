package ps.project.mapping.xml.cienciaVitae.contacts

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import ps.project.mapping.xml.cienciaVitae.common.CienciaVitaeXmlPayload
import ps.project.mapping.xml.cienciaVitae.common.CodeValueXml

@JacksonXmlRootElement(localName = "web-address", namespace = "http://www.cienciavitae.pt/ns/web-address")
data class WebsiteXml(
    val siteType: CodeValueXml,

    @field:JacksonXmlProperty(localName = "url")
    val url: String
) : CienciaVitaeXmlPayload()
