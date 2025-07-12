package ps.project.mapping.xml.lattes.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.lattes.TeamMember

data class LattesOtherBibliographicProductionXml(
    @JacksonXmlProperty(localName = "DADOS-BASICOS-DE-OUTRA-PRODUCAO")
    val basicData: LattesCommonDataProductionXml,

    @JacksonXmlProperty(localName = "AUTORES")
    val authors: List<TeamMember>? = null
)