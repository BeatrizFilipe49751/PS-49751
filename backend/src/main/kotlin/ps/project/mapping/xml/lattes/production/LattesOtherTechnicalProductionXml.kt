package ps.project.mapping.xml.lattes.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.lattes.TeamMember

data class LattesOtherTechnicalProductionXml (
    @JacksonXmlProperty(localName = "DADOS-BASICOS-DE-OUTRA-PRODUCAO-TECNICA")
    val basicData: LattesCommonDataProductionXml,

    @JacksonXmlProperty(localName = "DETALHAMENTO-DE-OUTRA-PRODUCAO-TECNICA")
    val details: LattesOtherTechnicalProductionDetailsXml,

    @JacksonXmlProperty(localName = "AUTORES")
    val authors: List<TeamMember>? = null,

    @JacksonXmlProperty(localName = "REGISTRO-OU-PATENTE")
    val patent: LattesPatentXml? = null,
)

data class LattesOtherTechnicalProductionDetailsXml(
    @JacksonXmlProperty(isAttribute = true, localName = "FINALIDADE")
    val desc: String,
)