package ps.project.mapping.xml.cienciaVitae.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.cienciaVitae.common.DateXml

data class JournalArticleXml(
    @JacksonXmlProperty(localName = "article-title")
    val title: String,

    val journal: String? = null,

    val volume: String? = null,

    @JacksonXmlProperty(localName = "page-range-from")
    val pageFrom: String? = null,

    @JacksonXmlProperty(localName = "page-range-to")
    val pageTo: String? = null,

    @JacksonXmlProperty(localName = "publication-date")
    val date: DateXml,

    val url: String? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    val identifiers: List<OutputIdentifierXml>? = null,

    val authors: OutputAuthorsWrapper? = null
)
