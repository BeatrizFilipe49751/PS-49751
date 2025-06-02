package ps.project.mapping.xml.lattes.project

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.lattes.TeamMember

data class ProjectTeam(
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "INTEGRANTES-DO-PROJETO")
    val members: List<TeamMember>? = null
)