package ps.project.mapping.xml.cienciaVitae.common

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class InstitutionsXml(
    @field:JacksonXmlProperty(isAttribute = true, localName = "total")
    val total: Int = 1,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "institution")
    val institution: List<InstitutionXml>
)
