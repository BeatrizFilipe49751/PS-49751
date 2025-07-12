package ps.project.mapping.xml.cienciaVitae.project

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class FundingIdentifiersXml(
    @JacksonXmlProperty(isAttribute = true, localName = "total")
    val total: Int,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "funding-identifier")
    val identifiers: List<FundingIdentifierXml>
)
