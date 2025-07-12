package ps.project.mapping.xml.lattes

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.lattes.activities.supervision.LattesSupervisionsXml

data class OtherProduction(
    @JacksonXmlProperty(localName = "ORIENTACOES-CONCLUIDAS")
    val supervisions: LattesSupervisionsXml? = null
)
