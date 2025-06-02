package ps.project.mapping.xml.lattes.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.lattes.TeamMember

data class LattesSoftwareXml(
    @JacksonXmlProperty(localName = "DADOS-BASICOS-DO-SOFTWARE")
    val basicData: BasicDataSoftwareXml,

    @JacksonXmlProperty(localName = "DETALHAMENTO-DO-SOFTWARE")
    val details: DetailsSoftwareXml,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "AUTORES")
    val authors: List<TeamMember>? = null
)

data class BasicDataSoftwareXml(
    @JacksonXmlProperty(localName = "TITULO-DO-SOFTWARE")
    val title: String,

    @JacksonXmlProperty(localName = "ANO")
    val ano: Int,

    @JacksonXmlProperty(localName = "DOI")
    val doi: String? = null,

    @JacksonXmlProperty(localName = "HOME-PAGE-DO-TRABALHO")
    val url: String? = null
)

data class DetailsSoftwareXml(
    @JacksonXmlProperty(localName = "PLATAFORMA")
    val platform: String?
)