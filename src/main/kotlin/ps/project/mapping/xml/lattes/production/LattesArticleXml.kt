package ps.project.mapping.xml.lattes.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.lattes.TeamMember

data class LattesArticleXml(
    @JacksonXmlProperty(localName = "DADOS-BASICOS-DO-ARTIGO")
    val basicData: LattesArticleBasicDataXml,

    @JacksonXmlProperty(localName = "DETALHAMENTO-DO-ARTIGO")
    val details: LattesArticleDetailsXml? = null,

    @JacksonXmlProperty(localName = "AUTORES")
    @JacksonXmlElementWrapper(useWrapping = false)
    val authors: List<TeamMember>? = null
)

data class LattesArticleBasicDataXml(
    @JacksonXmlProperty(isAttribute = true, localName = "TITULO-DO-ARTIGO")
    val title: String,

    @JacksonXmlProperty(isAttribute = true, localName = "ANO-DO-ARTIGO")
    val year: Int,

    @JacksonXmlProperty(isAttribute = true, localName = "DOI")
    val doi: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "HOME-PAGE-DO-TRABALHO")
    val url: String? = null
)

data class LattesArticleDetailsXml(
    @JacksonXmlProperty(isAttribute = true, localName = "ISSN")
    val issn: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "TITULO-DO-PERIODICO-OU-REVISTA")
    val journalTitle: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "VOLUME")
    val volume: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "PAGINA-INICIAL")
    val pageFrom: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "PAGINA-FINAL")
    val pageTo: String? = null
)