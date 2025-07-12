package ps.project.mapping.xml.cienciaVitae.project

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import ps.project.mapping.xml.cienciaVitae.common.CienciaVitaeXmlPayload
import ps.project.mapping.xml.cienciaVitae.common.CodeValueXml
import ps.project.mapping.xml.cienciaVitae.common.DateXml
import ps.project.mapping.xml.cienciaVitae.common.InstitutionsXml

@JacksonXmlRootElement(localName = "funding", namespace = "http://www.cienciavitae.pt/ns/funding")
data class ProjectXml(
    val fundingCategory: CodeValueXml,

    @JacksonXmlProperty(localName = "project-title")
    val title: String,

    @JacksonXmlProperty(localName = "project-description")
    val description: String? = null,

    @JacksonXmlProperty(localName = "startDateParticipation")
    val startDate: DateXml,

    @JacksonXmlProperty(localName = "endDateParticipation")
    val endDate: DateXml? = null,

    val status: CodeValueXml? = null,

    @JacksonXmlProperty(localName = "investigationRoleDescription")
    val role: CodeValueXml? = null,

    val institution: InstitutionsXml? = null,

    val fundingIdentifiers: FundingIdentifiersXml? = null,

    val investigators: InvestigatorsXml
) : CienciaVitaeXmlPayload()
