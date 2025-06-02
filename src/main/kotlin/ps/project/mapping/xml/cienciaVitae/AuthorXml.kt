package ps.project.mapping.xml.cienciaVitae

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import ps.project.mapping.xml.cienciaVitae.common.CienciaVitaeXmlPayload

@JacksonXmlRootElement(localName = "citation-name", namespace = "http://www.cienciavitae.pt/ns/citation-name")
data class AuthorXml(
    val value: String
) : CienciaVitaeXmlPayload()
