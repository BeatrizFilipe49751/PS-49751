package ps.project.mapping.xml.lattes.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.lattes.TeamMember

data class LattesTextXml(
    @JacksonXmlProperty(localName = "DADOS-BASICOS-DO-TEXTO")
    val basicData: LattesTextBasicDataXml,

    @JacksonXmlProperty(localName = "DETALHAMENTO-DO-TEXTO")
    val details: LattesTextDetailsXml,

    @JacksonXmlProperty(localName = "AUTORES")
    @JacksonXmlElementWrapper(useWrapping = false)
    val authors: List<TeamMember>? = null
)

data class LattesTextBasicDataXml(
    @JacksonXmlProperty(isAttribute = true, localName = "TITULO-DO-TEXTO")
    val title: String,

    @JacksonXmlProperty(isAttribute = true, localName = "NATUREZA")
    val nature: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "DOI")
    val doi: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "HOME-PAGE-DO-TRABALHO")
    val url: String? = null
)

data class LattesTextDetailsXml(
    @JacksonXmlProperty(isAttribute = true, localName = "TITULO-DO-JORNAL-OU-REVISTA")
    val journalTitle: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "ISSN")
    val issn: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "DATA-DE-PUBLICACAO")
    val publicationDate: String,

    @JacksonXmlProperty(isAttribute = true, localName = "VOLUME")
    val volume: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "PAGINA-INICIAL")
    val pageFrom: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "PAGINA-FINAL")
    val pageTo: String? = null
)