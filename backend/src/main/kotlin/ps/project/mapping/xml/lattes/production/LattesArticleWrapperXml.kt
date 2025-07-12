package ps.project.mapping.xml.lattes.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesArticleWrapperXml(
    @JacksonXmlProperty(localName = "ARTIGO-PUBLICADO")
    @JacksonXmlElementWrapper(useWrapping = false)
    val articles: List<LattesArticleXml>? = null
)
