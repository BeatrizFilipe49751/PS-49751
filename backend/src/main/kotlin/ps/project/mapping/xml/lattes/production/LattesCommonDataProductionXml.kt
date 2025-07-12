package ps.project.mapping.xml.lattes.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesCommonDataProductionXml(
    @JacksonXmlProperty(isAttribute = true, localName = "TITULO")
    val title: String,

    @JacksonXmlProperty(isAttribute = true, localName = "ANO")
    val year: Int,

    @JacksonXmlProperty(isAttribute = true, localName = "DOI")
    val doi: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "HOME-PAGE-DO-TRABALHO")
    val url: String? = null
)