package ps.project.mapping.xml.lattes.activities.supervision

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesMasterSupervisionXml(
    @JacksonXmlProperty(localName = "DADOS-BASICOS-DE-ORIENTACOES-CONCLUIDAS-PARA-MESTRADO")
    val basicData: LattesSupervisionBasicDataXml,

    @JacksonXmlProperty(localName = "DETALHAMENTO-DE-ORIENTACOES-CONCLUIDAS-PARA-MESTRADO")
    val details: LattesSupervisionDetailsXml
)