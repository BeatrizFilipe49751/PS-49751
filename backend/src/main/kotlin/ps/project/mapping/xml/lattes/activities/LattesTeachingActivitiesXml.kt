package ps.project.mapping.xml.lattes.activities

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesTeachingActivitiesXml(
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ENSINO")
    val teachingActivities: List<LattesTeachingXml>? = null
)
