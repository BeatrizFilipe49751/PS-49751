package ps.project.mapping.xml.lattes.activities

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesConsultingXml(
    @JacksonXmlProperty(isAttribute = true, localName = "MES-INICIO")
    val startMonth: Int,

    @JacksonXmlProperty(isAttribute = true, localName = "MES-FIM")
    val endMonth: Int? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "ANO-INICIO")
    val startYear: Int,

    @JacksonXmlProperty(isAttribute = true, localName = "ANO-FIM")
    val endYear: Int? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "ESPECIFICACAO")
    val title: String
)
