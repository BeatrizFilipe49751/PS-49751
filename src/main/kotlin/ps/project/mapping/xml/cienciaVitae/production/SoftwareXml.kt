package ps.project.mapping.xml.cienciaVitae.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.cienciaVitae.common.DateXml

data class SoftwareXml(
    @JacksonXmlProperty(localName = "title")
    val title: String,

    val description: String? = null,

    val version: String,

    val platform: String,

    @JacksonXmlProperty(localName = "publication-date")
    val date: DateXml,

    @JacksonXmlElementWrapper(useWrapping = false)
    val identifiers: List<OutputIdentifierXml>? = null,

    val authors: OutputAuthorsWrapper? = null
)
