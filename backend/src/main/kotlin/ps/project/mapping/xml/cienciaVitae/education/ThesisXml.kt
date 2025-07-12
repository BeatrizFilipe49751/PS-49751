package ps.project.mapping.xml.cienciaVitae.education

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class ThesisXml(
    @JacksonXmlProperty(localName = "thesis-title")
    val thesisTitle: String,

    @JacksonXmlElementWrapper(localName = "supervisors", useWrapping = true)
    @JacksonXmlProperty(localName = "supervisor")
    val supervisors: List<SupervisorXml>? = null
)
