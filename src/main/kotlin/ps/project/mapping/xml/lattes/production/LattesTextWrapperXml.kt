package ps.project.mapping.xml.lattes.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesTextWrapperXml(
    @JacksonXmlProperty(localName = "TEXTO-EM-JORNAL-OU-REVISTA")
    @JacksonXmlElementWrapper(useWrapping = false)
    val texts: List<LattesTextXml>? = null
)
