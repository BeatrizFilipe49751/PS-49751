package ps.project.mapping.xml.lattes.activities

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesJuryParticipationWrapper(
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "PARTICIPACAO-EM-BANCA-DE-MESTRADO")
    val masters: List<LattesJuryParticipation>? = emptyList(),

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "PARTICIPACAO-EM-BANCA-DE-DOUTORADO")
    val doctorate: List<LattesJuryParticipation>? = emptyList(),

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "PARTICIPACAO-EM-BANCA-DE-GRADUACAO")
    val other: List<LattesJuryParticipation>? = emptyList()
)
