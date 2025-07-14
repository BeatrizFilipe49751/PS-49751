package ps.project.mapping.xml.cienciaVitae.activity

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.cienciaVitae.common.DateXml

data class ConsultingAdvisoryXml (
    @JacksonXmlProperty(localName = "activity-description")
    val title: String,

    @JacksonXmlProperty(localName = "start-date")
    val startDate: DateXml,

    @JacksonXmlProperty(localName = "end-date")
    val endDate: DateXml? = null,
)