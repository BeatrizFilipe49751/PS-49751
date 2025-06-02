package ps.project.mapping.xml.cienciaVitae.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.cienciaVitae.common.DateXml

data class ResearchTechniqueXml(
    val description: String,

    @JacksonXmlProperty(localName = "publication-date")
    val date: DateXml,

    @JacksonXmlElementWrapper(useWrapping = false)
    val identifiers: List<OutputIdentifierXml>? = null,

    val authors: OutputAuthorsWrapper? = null
)
