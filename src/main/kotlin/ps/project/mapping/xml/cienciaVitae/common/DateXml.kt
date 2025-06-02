package ps.project.mapping.xml.cienciaVitae.common

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class DateXml(
    @field:JacksonXmlProperty(isAttribute = true)
    val year: Int,

    @field:JacksonXmlProperty(isAttribute = true)
    val month: Int,

    @field:JacksonXmlProperty(isAttribute = true)
    val day: Int
)