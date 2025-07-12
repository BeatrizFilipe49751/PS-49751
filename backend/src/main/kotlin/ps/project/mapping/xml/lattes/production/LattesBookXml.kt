package ps.project.mapping.xml.lattes.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.lattes.TeamMember

data class LattesBookXml(
    @JacksonXmlProperty(localName = "DADOS-BASICOS-DO-LIVRO")
    val basicData: LattesBookBasicDataXml,

    @JacksonXmlProperty(localName = "DETALHAMENTO-DO-LIVRO")
    val details: LattesBookDetailsXml? = null,

    @JacksonXmlProperty(localName = "AUTORES")
    @JacksonXmlElementWrapper(useWrapping = false)
    val authors: List<TeamMember>? = null
)

data class LattesBookBasicDataXml(
    @JacksonXmlProperty(isAttribute = true, localName = "TITULO-DO-LIVRO")
    val title: String,

    @JacksonXmlProperty(isAttribute = true, localName = "ANO")
    val year: Int,

    @JacksonXmlProperty(isAttribute = true, localName = "DOI")
    val doi: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "HOME-PAGE-DO-TRABALHO")
    val url: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "TIPO")
    val type: String? = null
)

data class LattesBookDetailsXml(
    @JacksonXmlProperty(isAttribute = true, localName = "ISBN")
    val isbn: String? = null,
)