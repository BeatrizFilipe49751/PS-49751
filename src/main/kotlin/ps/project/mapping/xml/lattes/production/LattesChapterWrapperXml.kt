package ps.project.mapping.xml.lattes.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesChapterWrapperXml(
    @JacksonXmlProperty(localName = "CAPITULO-DE-LIVRO-PUBLICADO")
    @JacksonXmlElementWrapper(useWrapping = false)
    val chapters: List<LattesChapterXml>? = null
)
