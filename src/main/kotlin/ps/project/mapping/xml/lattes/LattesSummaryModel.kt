package ps.project.mapping.xml.lattes

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesSummaryModel(
    @JacksonXmlProperty(isAttribute = true, localName = "TEXTO-RESUMO-CV-RH")
    val summary: String? = null
)
