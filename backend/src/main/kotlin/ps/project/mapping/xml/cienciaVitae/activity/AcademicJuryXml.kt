package ps.project.mapping.xml.cienciaVitae.activity

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.cienciaVitae.common.CodeValueXml
import ps.project.mapping.xml.cienciaVitae.common.DateXml
import ps.project.mapping.xml.cienciaVitae.common.InstitutionsXml

data class AcademicJuryXml (
    @JacksonXmlProperty(localName = "theme")
    val title: String,

    @JacksonXmlProperty(localName = "date")
    val date: DateXml,

    @JacksonXmlProperty(localName = "student-name")
    val student: String,

    @JacksonXmlProperty(localName = "examination-degree")
    val degree: CodeValueXml,

    @JacksonXmlProperty(localName = "examination-subject")
    val course: String? = null,

    val institutions: InstitutionsXml? = null,
)