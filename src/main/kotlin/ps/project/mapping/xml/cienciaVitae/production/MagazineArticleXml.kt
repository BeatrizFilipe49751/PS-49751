package ps.project.mapping.xml.cienciaVitae.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.cienciaVitae.common.DateXml

data class MagazineArticleXml(
    @JacksonXmlProperty(localName = "article-title")
    val title: String,

    val magazine: String? = null,

    val volume: Int? = null,

    @JacksonXmlProperty(localName = "page-range-from")
    val pageRangeFrom: Int? = null,

    @JacksonXmlProperty(localName = "page-range-to")
    val pageRangeTo: Int? = null,

    @JacksonXmlProperty(localName = "publication-date")
    val publicationDate: DateXml,

    val url: String? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    val identifiers: List<OutputIdentifierXml>? = null,

    val authors: OutputAuthorsWrapper? = null
)
