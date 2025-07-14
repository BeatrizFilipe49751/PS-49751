package ps.project.mapping.xml.cienciaVitae.activity

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.cienciaVitae.common.DateXml

data class CourseTaughtXml (
    @JacksonXmlProperty(localName = "academic-session")
    val title: String,

    @JacksonXmlProperty(localName = "start-date")
    val startDate: DateXml,

    @JacksonXmlProperty(localName = "end-date")
    val endDate: DateXml? = null,

    @JacksonXmlProperty(localName = "course-title")
    val course: String? = null,

    @JacksonXmlProperty(localName = "course-code")
    val courseCode: String? = null,
)