package ps.project.mapping.xml.lattes.project

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class ProjectFundings(
    @JacksonXmlProperty(localName = "FINANCIADOR-DO-PROJETO")
    val funding: ProjectFunding? = null
)
