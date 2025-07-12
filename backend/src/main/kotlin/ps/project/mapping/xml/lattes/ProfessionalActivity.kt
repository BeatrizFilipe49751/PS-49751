package ps.project.mapping.xml.lattes

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.lattes.activities.LattesConsultingActivitiesXml
import ps.project.mapping.xml.lattes.activities.LattesTeachingActivitiesXml
import ps.project.mapping.xml.lattes.project.ProjectActivities

data class ProfessionalActivity(
    @JacksonXmlProperty(localName = "ATIVIDADES-DE-PARTICIPACAO-EM-PROJETO")
    val projectActivities: ProjectActivities? = null,

    @JacksonXmlProperty(localName = "ATIVIDADES-DE-ENSINO")
    val teachingActivities: LattesTeachingActivitiesXml? = null,

    @JacksonXmlProperty(localName = "ATIVIDADES-DE-CONSELHO-COMISSAO-E-CONSULTORIA")
    val consultingActivities: LattesConsultingActivitiesXml? = null
)