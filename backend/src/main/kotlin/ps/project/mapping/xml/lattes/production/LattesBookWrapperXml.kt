package ps.project.mapping.xml.lattes.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesBookWrapperXml(
    @JacksonXmlProperty(localName = "LIVRO-PUBLICADO-OU-ORGANIZADO")
    @JacksonXmlElementWrapper(useWrapping = false)
    val books: List<LattesBookXml>? = null
)
