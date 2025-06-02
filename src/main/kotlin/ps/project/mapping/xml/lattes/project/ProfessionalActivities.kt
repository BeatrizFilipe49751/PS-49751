package ps.project.mapping.xml.lattes.project

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class ProfessionalActivities(
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ATUACAO-PROFISSIONAL")
    val activities: List<ProfessionalActivity>? = null
)