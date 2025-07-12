package ps.project.mapping.xml.cienciaVitae.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "authors")
data class OutputAuthorsWrapper(
    @JacksonXmlProperty(isAttribute = true, localName = "total")
    val total: Int = 0,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "author")
    val authors: List<OutputAuthorXml> = emptyList(),

    @JacksonXmlProperty(localName = "citation")
    val citation: String
)
