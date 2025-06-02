package ps.project.mapping.xml.cienciaVitae.common

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import java.time.Instant
import java.time.format.DateTimeFormatter

open class CienciaVitaeXmlPayload(
    @field:JacksonXmlProperty(isAttribute = true, localName = "privacy-level")
    val privacyLevel: String? = "PUBLICO",

    @field:JacksonXmlProperty(isAttribute = true, localName = "last-modified-date")
    val modifiedDate: String = DateTimeFormatter.ISO_INSTANT.format(Instant.now()),
)