package ps.project.mapping.xml.lattes.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.lattes.activities.LattesEventXml

data class LattesOtherTechnicalProductionsXml (
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "MIDIA-SOCIAL-WEBSITE-BLOG")
    val websites: List<LattesWebsiteXml>? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "RELATORIO-DE-PESQUISA")
    val reports: List<LattesResearchReportXml>? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "OUTRA-PRODUCAO-TECNICA")
    val others: List<LattesOtherTechnicalProductionXml>? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ORGANIZACAO-DE-EVENTO")
    val events: List<LattesEventXml>? = null,
)