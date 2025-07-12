package ps.project.mapping.xml.lattes.activities.supervision

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesPhdSupervisionXml(
    @JacksonXmlProperty(localName = "DADOS-BASICOS-DE-ORIENTACOES-CONCLUIDAS-PARA-DOUTORADO")
    val basicData: LattesSupervisionBasicDataXml,

    @JacksonXmlProperty(localName = "DETALHAMENTO-DE-ORIENTACOES-CONCLUIDAS-PARA-DOUTORADO")
    val details: LattesSupervisionDetailsXml
)
