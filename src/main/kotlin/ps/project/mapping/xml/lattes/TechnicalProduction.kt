package ps.project.mapping.xml.lattes

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
//import ps.project.mapping.xml.lattes.production.LattesOtherTechnicalProductionXml
import ps.project.mapping.xml.lattes.production.LattesReportXml
import ps.project.mapping.xml.lattes.production.LattesResearchTechniqueXml
import ps.project.mapping.xml.lattes.production.LattesSoftwareXml

data class TechnicalProduction(
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "SOFTWARE")
    val softwares: List<LattesSoftwareXml>? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "TRABALHO-TECNICO")
    val reports: List<LattesReportXml>? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "PROCESSOS-OU-TECNICAS")
    val researchTechnique: List<LattesResearchTechniqueXml>? = null,
/*
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "DEMAIS-TIPOS-DE-PRODUCAO-TECNICA")
    val others: List<LattesOtherTechnicalProductionXml>? = null*/
)