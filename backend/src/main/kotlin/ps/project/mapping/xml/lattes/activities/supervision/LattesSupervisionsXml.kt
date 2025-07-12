package ps.project.mapping.xml.lattes.activities.supervision

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesSupervisionsXml(
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ORIENTACOES-CONCLUIDAS-PARA-MESTRADO")
    val masterSupervisions: List<LattesMasterSupervisionXml>? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ORIENTACOES-CONCLUIDAS-PARA-DOUTORADO")
    val phdSupervisions: List<LattesPhdSupervisionXml>? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ORIENTACOES-CONCLUIDAS-PARA-POS-DOUTORADO")
    val postdocSupervisions: List<LattesPostdocSupervisionXml>? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "OUTRAS-ORIENTACOES-CONCLUIDAS")
    val otherSupervisions: List<LattesOtherSupervisionXml>? = null
)
