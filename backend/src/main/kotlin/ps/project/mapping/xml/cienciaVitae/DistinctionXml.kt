package ps.project.mapping.xml.cienciaVitae

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import ps.project.mapping.xml.cienciaVitae.common.CienciaVitaeXmlPayload
import ps.project.mapping.xml.cienciaVitae.common.CodeValueXml
import ps.project.mapping.xml.cienciaVitae.common.InstitutionsXml

@JacksonXmlRootElement(localName = "distinction", namespace = "http://www.cienciavitae.pt/ns/distinction")
data class DistinctionXml(
    val distinctionType: CodeValueXml,

    @field:JacksonXmlProperty(localName = "distinction-name")
    val distinctionName: String,

    @field:JacksonXmlProperty(localName = "effective-date")
    val effectiveDate: Int,

    val institutions: InstitutionsXml? = null
): CienciaVitaeXmlPayload()
