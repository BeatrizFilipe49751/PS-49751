package ps.project.mapping.xml.cienciaVitae.activity

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import ps.project.mapping.xml.cienciaVitae.common.CienciaVitaeXmlPayload
import ps.project.mapping.xml.cienciaVitae.common.CodeValueXml

@JacksonXmlRootElement(localName = "service", namespace = "http://www.cienciavitae.pt/ns/service")
data class ServiceXml(
    @JacksonXmlProperty(localName = "service-category")
    val serviceCategory: CodeValueXml,

    @JacksonXmlProperty(localName = "consulting-advisory")
    val consultingAdvisory: ConsultingAdvisoryXml? = null,

    @JacksonXmlProperty(localName = "event-administration")
    val eventAdministration: EventAdministrationXml? = null,

    @JacksonXmlProperty(localName = "research-based-degree-supervision")
    val supervision: SupervisionXml? = null,

    @JacksonXmlProperty(localName = "graduate-examination")
    val academicJury: AcademicJuryXml? = null,

    @JacksonXmlProperty(localName = "course-taught")
    val courseTaught: CourseTaughtXml? = null
) : CienciaVitaeXmlPayload()
