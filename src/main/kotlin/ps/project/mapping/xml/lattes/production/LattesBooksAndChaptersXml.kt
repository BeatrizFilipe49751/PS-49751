package ps.project.mapping.xml.lattes.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesBooksAndChaptersXml(
    @JacksonXmlProperty(localName = "LIVROS-PUBLICADOS-OU-ORGANIZADOS")
    val books: LattesBookWrapperXml? = null,

    @JacksonXmlProperty(localName = "CAPITULOS-DE-LIVROS-PUBLICADOS")
    val chapters: LattesChapterWrapperXml? = null
)
