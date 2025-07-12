package ps.project.mapping.xml.lattes.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.lattes.TeamMember

data class LattesTranslationXml(
    @JacksonXmlProperty(localName = "DADOS-BASICOS-DA-TRADUCAO")
    val basicData: LattesCommonDataProductionXml,

    @JacksonXmlProperty(localName = "DETALHAMENTO-DA-TRADUCAO")
    val details: LattesTranslationDetailsXml? = null,

    @JacksonXmlProperty(localName = "AUTORES")
    val authors: List<TeamMember>? = null
)

data class LattesTranslationDetailsXml(
    @JacksonXmlProperty(isAttribute = true, localName = "VOLUME")
    val volume: String? = null
)