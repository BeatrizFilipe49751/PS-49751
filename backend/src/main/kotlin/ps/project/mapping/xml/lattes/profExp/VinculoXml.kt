package ps.project.mapping.xml.lattes.profExp

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class VinculoXml (
    @JacksonXmlProperty(localName = "ANO-INICIO", isAttribute = true)
    val startYear: String,

    @JacksonXmlProperty(localName = "MES-INICIO", isAttribute = true)
    val startMonth: String,

    @JacksonXmlProperty(localName = "ANO-FIM", isAttribute = true)
    val endYear: String? = null,

    @JacksonXmlProperty(localName = "MES-FIM", isAttribute = true)
    val endMonth: String? = null,

    @JacksonXmlProperty(localName = "OUTRAS-INFORMACOES", isAttribute = true)
    val description: String? = null,
)