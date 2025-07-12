package ps.project.mapping.xml.cienciaVitae.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.cienciaVitae.common.DateXml

data class ReportXml(
    @JacksonXmlProperty(localName = "report-title")
    val reportTitle: String,

    val volume: String? = null,

    @JacksonXmlProperty(localName = "date-submitted")
    val date: DateXml,

    val url: String? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    val identifiers: List<OutputIdentifierXml>? = null,

    val authors: OutputAuthorsWrapper? = null
)
