package ps.project.mapping.xml.lattes

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.lattes.activities.LattesJuryParticipationWrapper

data class LattesComplementaryData(
    @JacksonXmlProperty(localName = "FORMACAO-COMPLEMENTAR")
    val complementaryEducation: LattesComplementaryEducation? = null,
)

data class LattesComplementaryEducation(
    @JacksonXmlProperty(localName = "PARTICIPACAO-EM-BANCA-TRABALHOS-CONCLUSAO")
    val juryParticipation: LattesJuryParticipationWrapper? = null,
)