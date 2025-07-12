package ps.project.mapping.xml.lattes.activities

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText

data class LattesSubjectXml(
    @field:JacksonXmlProperty(localName = "SEQUENCIA-ESPECIFICACAO", isAttribute = true)
    val sequence: String? = null
){
    @field:JacksonXmlText
    val name: String = ""
}
