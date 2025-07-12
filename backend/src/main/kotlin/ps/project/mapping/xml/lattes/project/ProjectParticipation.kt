package ps.project.mapping.xml.lattes.project

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class ProjectParticipation(
    @JacksonXmlProperty(isAttribute = true, localName = "NOME-ORGAO")
    val orgName: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "NOME-UNIDADE")
    val unitName: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "ANO-INICIO")
    val startYear: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "MES-INICIO")
    val startMonth: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "ANO-FIM")
    val endYear: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "MES-FIM")
    val endMonth: String? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "PROJETO-DE-PESQUISA")
    val researchProject: List<ResearchProject>? = null
)