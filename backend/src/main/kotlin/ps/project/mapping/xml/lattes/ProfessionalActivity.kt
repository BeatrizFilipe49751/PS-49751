package ps.project.mapping.xml.lattes

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import org.w3c.dom.Element
import ps.project.mapping.xml.lattes.activities.LattesConsultingActivitiesXml
import ps.project.mapping.xml.lattes.activities.LattesTeachingActivitiesXml
import ps.project.mapping.xml.lattes.profExp.VinculoXml
import ps.project.mapping.xml.lattes.project.ProjectActivities

data class ProfessionalActivity(
    @JacksonXmlProperty(isAttribute = true, localName = "NOME-INSTITUICAO")
    val institution: String,

    @JacksonXmlProperty(localName = "VINCULOS")
    @JacksonXmlElementWrapper(useWrapping = false)
    val vinculos: List<VinculoXml>,

    @JacksonXmlProperty(localName = "ATIVIDADES-DE-PARTICIPACAO-EM-PROJETO")
    val projectActivities: ProjectActivities? = null,

    @JacksonXmlProperty(localName = "ATIVIDADES-DE-ENSINO")
    val teachingActivities: LattesTeachingActivitiesXml? = null,

    @JacksonXmlProperty(localName = "ATIVIDADES-DE-CONSELHO-COMISSAO-E-CONSULTORIA")
    val consultingActivities: LattesConsultingActivitiesXml? = null,
)