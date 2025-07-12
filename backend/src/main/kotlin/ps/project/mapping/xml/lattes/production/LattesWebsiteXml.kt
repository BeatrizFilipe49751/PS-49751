package ps.project.mapping.xml.lattes.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.lattes.TeamMember

data class LattesWebsiteXml (
    @JacksonXmlProperty(localName = "DADOS-BASICOS-DA-MIDIA-SOCIAL-WEBSITE-BLOG")
    val basicData: LattesWebsiteBasicDataXml,

    @JacksonXmlProperty(localName = "DETALHAMENTO-DA-MIDIA-SOCIAL-WEBSITE-BLOG")
    val details: LattesWebsiteDetailsXml? = null,

    @JacksonXmlProperty(localName = "AUTORES")
    val authors: List<TeamMember>? = null
)

data class LattesWebsiteBasicDataXml(
    @JacksonXmlProperty(isAttribute = true, localName = "TITULO")
    val title: String,

    @JacksonXmlProperty(isAttribute = true, localName = "ANO")
    val year: Int,

    @JacksonXmlProperty(isAttribute = true, localName = "HOME-PAGE")
    val url: String? = null
)

data class LattesWebsiteDetailsXml(
    @JacksonXmlProperty(isAttribute = true, localName = "TEMA")
    val theme: String? = null
)