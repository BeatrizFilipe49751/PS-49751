package ps.project.mapping.xml.lattes.activities.supervision

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesOtherSupervisionXml(
    @JacksonXmlProperty(localName = "DADOS-BASICOS-DE-OUTRAS-ORIENTACOES-CONCLUIDAS")
    val basicData: LattesOtherSupervisionBasicDataXml,

    @JacksonXmlProperty(localName = "DETALHAMENTO-DE-OUTRAS-ORIENTACOES-CONCLUIDAS")
    val details: LattesSupervisionDetailsXml
)

data class LattesOtherSupervisionBasicDataXml(
    @JacksonXmlProperty(isAttribute = true, localName = "TITULO")
    val title: String,

    @JacksonXmlProperty(isAttribute = true, localName = "ANO")
    val year: Int,

    @JacksonXmlProperty(isAttribute = true, localName = "NATUREZA")
    val type: String
)