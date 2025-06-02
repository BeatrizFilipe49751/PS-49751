package ps.project.mapping.xml.lattes.project

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.domain.project.ProjectState

data class ResearchProject(
    @JacksonXmlProperty(isAttribute = true, localName = "NOME-DO-PROJETO")
    val title: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "DESCRICAO-DO-PROJETO")
    val description: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "IDENTIFICADOR-PROJETO")
    val identifier: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "SITUACAO")
    val state: String? = null,

    @JacksonXmlProperty(localName = "EQUIPE-DO-PROJETO")
    val team: ProjectTeam? = null,

    @JacksonXmlProperty(localName = "FINANCIADORES-DO-PROJETO")
    val funding: ProjectFundings? = null
) {
    fun mapState(): ProjectState? {
        return when (state?.uppercase()) {
            "EM_ANDAMENTO" -> ProjectState.Ongoing
            "CONCLUIDO" -> ProjectState.Concluded
            "DESATIVADO" -> ProjectState.Cancelled
            else -> null
        }
    }
}