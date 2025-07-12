package ps.project.mapping.xml.lattes.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.lattes.TeamMember

data class LattesResearchTechniqueXml(
    @JacksonXmlProperty(localName = "DADOS-BASICOS-DO-PROCESSOS-OU-TECNICAS")
    val basicData: BasicDataResearchTechniqueXml,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "AUTORES")
    val authors: List<TeamMember>? = null
)

data class BasicDataResearchTechniqueXml(
    @JacksonXmlProperty(isAttribute = true, localName = "TITULO-DO-PROCESSO")
    val title: String,

    @JacksonXmlProperty(isAttribute = true, localName = "ANO")
    val year: Int,

    @JacksonXmlProperty(isAttribute = true, localName = "DOI")
    val doi: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "HOME-PAGE-DO-TRABALHO")
    val url: String? = null
)