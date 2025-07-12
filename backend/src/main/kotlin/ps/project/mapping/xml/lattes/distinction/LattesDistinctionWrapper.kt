package ps.project.mapping.xml.lattes.distinction

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesDistinctionWrapper(
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "PREMIO-TITULO")
    val distinctions: List<LattesDistinctionXml> = emptyList()
)