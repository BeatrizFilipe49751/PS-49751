package ps.project.mapping.xml.lattes.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.lattes.TeamMember

data class LattesResearchReportXml (
    @JacksonXmlProperty(localName = "DADOS-BASICOS-DO-RELATORIO-DE-PESQUISA")
    val basicData: LattesCommonDataProductionXml,

    @JacksonXmlProperty(localName = "AUTORES")
    val authors: List<TeamMember>? = null
)