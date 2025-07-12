package ps.project.mapping.xml.lattes.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesOtherBibliographicProductionsXml(
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "TRADUCAO")
    val translations: List<LattesTranslationXml>? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "OUTRA-PRODUCAO-BIBLIOGRAFICA")
    val others: List<LattesOtherBibliographicProductionXml>? = null,
)
