package ps.project.mapping.xml.cienciaVitae.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class EditedBookXml (
    @JacksonXmlProperty(localName = "title")
    val title: String,

    val volume: Int? = null,

    @JacksonXmlProperty(localName = "publication-year")
    val year: Int,

    val url: String? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    val identifiers: List<OutputIdentifierXml>? = null,

    val authors: OutputAuthorsWrapper? = null
)