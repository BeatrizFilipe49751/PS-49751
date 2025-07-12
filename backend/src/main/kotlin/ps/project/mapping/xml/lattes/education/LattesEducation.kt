package ps.project.mapping.xml.lattes.education

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesEducation(
    @JacksonXmlProperty(isAttribute = true, localName = "NOME-INSTITUICAO")
    val institution: String,

    @JacksonXmlProperty(isAttribute = true, localName = "NOME-CURSO")
    val course: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "STATUS-DO-CURSO")
    val status: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "CODIGO-CURSO")
    val courseCode: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "ANO-DE-INICIO")
    val startYear: Int? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "ANO-DE-CONCLUSAO")
    val endYear: Int,

    @JacksonXmlProperty(isAttribute = true, localName = "TITULO-DA-DISSERTACAO-TESE")
    val thesisTitle: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "TITULO-DO-TRABALHO-DE-CONCLUSAO-DE-CURSO")
    val finalProjectTitle: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "NOME-COMPLETO-DO-ORIENTADOR")
    val supervisorName: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "NOME-DO-CO-ORIENTADOR")
    val coSupervisorName: String? = null
)
