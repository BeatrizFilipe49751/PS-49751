package ps.project.mapping.xml.cienciaVitae.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper

open class OutputCommonXml(
    @JacksonXmlElementWrapper(useWrapping = false)
    val identifiers: List<OutputIdentifierXml>? = null,

    val authors: OutputAuthorsWrapper? = null
)
