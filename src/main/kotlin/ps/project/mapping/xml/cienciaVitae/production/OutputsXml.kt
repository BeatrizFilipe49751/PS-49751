package ps.project.mapping.xml.cienciaVitae.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import ps.project.mapping.xml.cienciaVitae.common.CienciaVitaeXmlPayload

@JacksonXmlRootElement(localName = "outputs", namespace = "http://www.cienciavitae.pt/ns/output")
data class OutputsXml(
    @field:JacksonXmlProperty(isAttribute = true, localName = "total")
    val total: Int,

    @JacksonXmlElementWrapper(useWrapping = false)
    val output: List<OutputXml>
) : CienciaVitaeXmlPayload(privacyLevel = null)