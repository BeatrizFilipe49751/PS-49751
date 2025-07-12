package ps.project.mapping.xml.lattes.activities

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesJuryParticipation(
    @JacksonXmlProperty(localName = "DADOS-BASICOS-DA-PARTICIPACAO")
    val basicData: LattesJuryParticipationBasicData,

    @JacksonXmlProperty(localName = "DETALHAMENTO-DA-PARTICIPACAO")
    val details: LattesJuryParticipationDetails
)

data class LattesJuryParticipationBasicData(
    @JacksonXmlProperty(localName = "TITULO")
    val title: String,

    @JacksonXmlProperty(localName = "ANO")
    val year: Int
)

data class LattesJuryParticipationDetails(
    @JacksonXmlProperty(localName = "NOME-DO-CANDIDATO")
    val candidate: String,

    @JacksonXmlProperty(localName = "NOME-CURSO")
    val course: String? = null,

    @JacksonXmlProperty(localName = "NOME-INSTITUICAO")
    val institution: String? = null
)