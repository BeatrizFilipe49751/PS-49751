package ps.project.mapping.xml.lattes.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.lattes.TeamMember

data class LattesChapterXml(
    @JacksonXmlProperty(localName = "DADOS-BASICOS-DO-CAPITULO")
    val basicData: LattesChapterBasicDataXml,

    @JacksonXmlProperty(localName = "DETALHAMENTO-DO-CAPITULO")
    val details: LattesChapterDetailsXml? = null,

    @JacksonXmlProperty(localName = "AUTORES")
    @JacksonXmlElementWrapper(useWrapping = false)
    val authors: List<TeamMember>? = null
)

data class LattesChapterBasicDataXml(
    @JacksonXmlProperty(isAttribute = true, localName = "TITULO-DO-CAPITULO-DO-LIVRO")
    val title: String,

    @JacksonXmlProperty(isAttribute = true, localName = "ANO")
    val year: Int,

    @JacksonXmlProperty(isAttribute = true, localName = "DOI")
    val doi: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "HOME-PAGE-DO-TRABALHO")
    val url: String? = null
)

data class LattesChapterDetailsXml(
    @JacksonXmlProperty(isAttribute = true, localName = "TITULO-DO-LIVRO")
    val bookTitle: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "ISBN")
    val isbn: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "PAGINA-INICIAL")
    val pageFrom: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "PAGINA-FINAL")
    val pageTo: String? = null
)