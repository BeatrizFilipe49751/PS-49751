package ps.project.mapping.xml.lattes.activities.supervision

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesSupervisionBasicDataXml(
    @JacksonXmlProperty(isAttribute = true, localName = "TITULO")
    val title: String,

    @JacksonXmlProperty(isAttribute = true, localName = "ANO")
    val year: Int
)
