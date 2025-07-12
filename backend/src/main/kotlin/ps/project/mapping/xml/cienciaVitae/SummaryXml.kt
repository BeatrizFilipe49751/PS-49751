package ps.project.mapping.xml.cienciaVitae

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import ps.project.mapping.xml.cienciaVitae.common.CienciaVitaeXmlPayload

@JacksonXmlRootElement(localName = "resume", namespace = "http://www.cienciavitae.pt/ns/resume")
data class SummaryXml(
    val value: String
) : CienciaVitaeXmlPayload()
