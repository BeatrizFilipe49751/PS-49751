package ps.project.mapping.xml.lattes.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesPatentXml(
    @JacksonXmlProperty(isAttribute = true, localName = "TITULO-PATENTE")
    val title: String,

    @JacksonXmlProperty(isAttribute = true, localName = "DATA-DE-CONCESSAO")
    val date: String
)
