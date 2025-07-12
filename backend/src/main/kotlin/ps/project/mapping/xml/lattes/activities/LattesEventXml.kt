package ps.project.mapping.xml.lattes.activities

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesEventXml(
    @JacksonXmlProperty(localName = "DADOS-BASICOS-DA-ORGANIZACAO-DE-EVENTO")
    val basicData: LattesEventBasicDataXml,

    @JacksonXmlProperty(localName = "DETALHAMENTO-DA-ORGANIZACAO-DE-EVENTO")
    val details: LattesEventDetailsXml? = null
)

data class LattesEventBasicDataXml(
    @JacksonXmlProperty(isAttribute = true, localName = "TITULO")
    val title: String,

    @JacksonXmlProperty(isAttribute = true, localName = "ANO")
    val year: Int,
)

data class LattesEventDetailsXml(
    @JacksonXmlProperty(isAttribute = true, localName = "INSTITUICAO-PROMOTORA")
    val institution: String? = null,
)