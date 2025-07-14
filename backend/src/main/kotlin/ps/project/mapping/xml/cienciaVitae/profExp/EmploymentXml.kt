package ps.project.mapping.xml.cienciaVitae.profExp

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import ps.project.mapping.xml.cienciaVitae.common.CienciaVitaeXmlPayload
import ps.project.mapping.xml.cienciaVitae.common.CodeValueXml
import ps.project.mapping.xml.cienciaVitae.common.DateXml
import ps.project.mapping.xml.cienciaVitae.common.InstitutionXml

@JacksonXmlRootElement(localName = "employment", namespace = "http://www.cienciavitae.pt/ns/employment")
data class EmploymentXml (
    @JacksonXmlProperty(localName = "employment-category")
    val employmentCategory: CodeValueXml,

    val institution: InstitutionXml,

    @JacksonXmlProperty(localName = "start-date")
    val startDate: DateXml,

    @JacksonXmlProperty(localName = "end-date")
    val endDate: DateXml? = null,
) : CienciaVitaeXmlPayload()