package ps.project.mapping.xml.lattes.activities.supervision

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesPostdocSupervisionXml(
    @JacksonXmlProperty(localName = "DADOS-BASICOS-DE-ORIENTACOES-CONCLUIDAS-PARA-POS-DOUTORADO")
    val basicData: LattesSupervisionBasicDataXml,

    @JacksonXmlProperty(localName = "DETALHAMENTO-DE-ORIENTACOES-CONCLUIDAS-PARA-POS-DOUTORADO")
    val details: LattesSupervisionDetailsXml
)
