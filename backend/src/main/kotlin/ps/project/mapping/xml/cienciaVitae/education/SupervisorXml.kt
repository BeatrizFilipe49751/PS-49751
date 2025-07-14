package ps.project.mapping.xml.cienciaVitae.education

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.cienciaVitae.common.CodeValueXml

data class SupervisorXml(
    @JacksonXmlProperty(localName = "supervisor-name")
    val supervisorName: String,

    @JacksonXmlProperty(localName = "supervisor-role")
    val supervisorRole: CodeValueXml
)