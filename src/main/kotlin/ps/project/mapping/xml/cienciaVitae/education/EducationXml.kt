package ps.project.mapping.xml.cienciaVitae.education

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import ps.project.mapping.xml.cienciaVitae.common.CienciaVitaeXmlPayload
import ps.project.mapping.xml.cienciaVitae.common.CodeValueXml
import ps.project.mapping.xml.cienciaVitae.common.DateXml
import ps.project.mapping.xml.cienciaVitae.common.InstitutionsXml

@JacksonXmlRootElement(localName = "degree", namespace = "http://www.cienciavitae.pt/ns/degree")
data class EducationXml(
    @JacksonXmlProperty(localName = "degreeType")
    val degreeType: CodeValueXml,

    @JacksonXmlProperty(localName = "degreeCode")
    val degreeCode: CodeValueXml? = null,

    @JacksonXmlProperty(localName = "degree-name")
    val degreeName: String,

    val institutions: InstitutionsXml,

    val classification: String? = null,

    val degreeStatus: CodeValueXml,

    val startDate: DateXml? = null,

    val endDate: DateXml,

    val thesis: ThesisXml? = null
) : CienciaVitaeXmlPayload()