package ps.project.mapping.xml.lattes.project

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class ProjectActivities(
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "PARTICIPACAO-EM-PROJETO")
    val projectParticipations: List<ProjectParticipation>? = null
)