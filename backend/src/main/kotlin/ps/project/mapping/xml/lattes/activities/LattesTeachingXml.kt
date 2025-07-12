package ps.project.mapping.xml.lattes.activities

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText

data class LattesTeachingXml(
    @JacksonXmlProperty(isAttribute = true, localName = "MES-INICIO")
    val startMonth: Int,

    @JacksonXmlProperty(isAttribute = true, localName = "MES-FIM")
    val endMonth: Int? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "ANO-INICIO")
    val startYear: Int,

    @JacksonXmlProperty(isAttribute = true, localName = "ANO-FIM")
    val endYear: Int? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "CODIGO-CURSO")
    val degreeCode: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "NOME-CURSO")
    val degree: String? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "DISCIPLINA")
    val subjects: List<LattesSubjectXml>
)