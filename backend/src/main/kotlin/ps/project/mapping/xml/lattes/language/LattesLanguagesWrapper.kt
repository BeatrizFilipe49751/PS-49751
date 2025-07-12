package ps.project.mapping.xml.lattes.language

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesLanguagesWrapper(
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "IDIOMA")
    val languages: List<LattesLanguageXml> = emptyList()
)