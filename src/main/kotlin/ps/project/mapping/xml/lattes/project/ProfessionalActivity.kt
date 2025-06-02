package ps.project.mapping.xml.lattes.project

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class ProfessionalActivity(
    @JacksonXmlProperty(localName = "ATIVIDADES-DE-PARTICIPACAO-EM-PROJETO")
    val projectActivities: ProjectActivities? = null
)