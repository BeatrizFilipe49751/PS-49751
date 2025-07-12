package ps.project.mapping.xml.cienciaVitae.common

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class CodeValueXml(
    @field:JacksonXmlProperty(isAttribute = true)
    val code: String? = null,

    val value: String? = null
)
