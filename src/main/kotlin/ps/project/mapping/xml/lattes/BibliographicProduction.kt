package ps.project.mapping.xml.lattes

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.lattes.production.*

data class BibliographicProduction (
    @JacksonXmlProperty(localName = "TEXTOS-EM-JORNAIS-OU-REVISTAS")
    val texts: LattesTextWrapperXml? = null,

    @JacksonXmlProperty(localName = "ARTIGOS-PUBLICADOS")
    val articles: LattesArticleWrapperXml? = null,

    @JacksonXmlProperty(localName = "LIVROS-E-CAPITULOS")
    val booksAndChapters: LattesBooksAndChaptersXml? = null,

    @JacksonXmlProperty(localName = "DEMAIS-TIPOS-DE-PRODUCAO-BIBLIOGRAFICA")
    val other: LattesOtherBibliographicProductionsXml? = null
)