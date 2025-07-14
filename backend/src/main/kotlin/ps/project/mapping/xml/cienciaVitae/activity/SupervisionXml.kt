package ps.project.mapping.xml.cienciaVitae.activity

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText
import ps.project.mapping.xml.cienciaVitae.common.CodeValueXml
import ps.project.mapping.xml.cienciaVitae.common.DateXml
import ps.project.mapping.xml.cienciaVitae.common.InstitutionsXml

data class SupervisionXml (
    @JacksonXmlProperty(localName = "thesis-title")
    val title: String,

    @JacksonXmlProperty(localName = "start-date")
    val startDate: DateXml,

    @JacksonXmlProperty(localName = "student-name")
    val student: StudentXml,

    @JacksonXmlProperty(localName = "degree-subject")
    val course: String? = null,

    @JacksonXmlProperty(localName = "course-code")
    val courseCode: String? = null,

    @JacksonXmlProperty(localName = "supervisory-type")
    val supervisoryType: CodeValueXml,

    @JacksonXmlProperty(localName = "degree-type")
    val degree: CodeValueXml,

    @JacksonXmlProperty(localName = "academic-institutions")
    val institutions: InstitutionsXml? = null,
)

data class StudentXml (
    @JacksonXmlText
    val name: String,

    @JacksonXmlProperty(localName = "consent-obtained-for-name-release", isAttribute = true)
    val consent: Boolean
)