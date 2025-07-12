package ps.project.mapping.xml.lattes.activities.supervision

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesSupervisionDetailsXml(
    @JacksonXmlProperty(isAttribute = true, localName = "NOME-DO-ORIENTADO")
    val supervisee: String,

    @JacksonXmlProperty(isAttribute = true, localName = "TIPO-DE-ORIENTACAO")
    val role: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "NOME-DA-INSTITUICAO")
    val institution: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "CODIGO-CURSO")
    val degreeCode: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "NOME-DO-CURSO")
    val degree: String? = null,
)