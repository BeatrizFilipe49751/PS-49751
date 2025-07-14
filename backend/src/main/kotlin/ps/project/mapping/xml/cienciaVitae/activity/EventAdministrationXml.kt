package ps.project.mapping.xml.cienciaVitae.activity

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.cienciaVitae.common.DateXml
import ps.project.mapping.xml.cienciaVitae.common.InstitutionsXml

data class EventAdministrationXml (
    @JacksonXmlProperty(localName = "event-description")
    val title: String,

    @JacksonXmlProperty(localName = "event-start-date")
    val startDate: DateXml,

    val institutions: InstitutionsXml? = null,
)