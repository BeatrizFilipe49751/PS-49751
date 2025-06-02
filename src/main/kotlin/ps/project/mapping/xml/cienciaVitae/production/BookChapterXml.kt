package ps.project.mapping.xml.cienciaVitae.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class BookChapterXml(
    @JacksonXmlProperty(localName = "chapter-title")
    val title: String,

    @JacksonXmlProperty(localName = "book-title")
    val secondaryTitle: String? = null,

    @JacksonXmlProperty(localName = "book-volume")
    val volume: Int? = null,

    @JacksonXmlProperty(localName = "chapter-page-range-from")
    val pageFrom: Int? = null,

    @JacksonXmlProperty(localName = "chapter-page-range-to")
    val pageTo: Int? = null,

    @JacksonXmlProperty(localName = "publication-year")
    val year: Int,

    val url: String? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    val identifiers: List<OutputIdentifierXml>? = null,

    val authors: OutputAuthorsWrapper? = null
)
