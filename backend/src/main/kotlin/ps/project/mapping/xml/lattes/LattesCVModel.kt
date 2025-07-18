package ps.project.mapping.xml.lattes

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "CURRICULO-VITAE")
data class LattesCVModel(
    @JacksonXmlProperty(localName = "DADOS-GERAIS")
    val generalData: GeneralData? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "NUMERO-IDENTIFICADOR")
    val lattesIdentifier: String? = null,

    @JacksonXmlProperty(localName = "PRODUCAO-TECNICA")
    val technicalProduction: TechnicalProduction? = null,

    @JacksonXmlProperty(localName = "PRODUCAO-BIBLIOGRAFICA")
    val bibliographicProduction: BibliographicProduction? = null,

    @JacksonXmlProperty(localName = "OUTRA-PRODUCAO")
    val otherProduction: OtherProduction? = null,

    @JacksonXmlProperty(localName = "DADOS-COMPLEMENTARES")
    val complementaryData: LattesComplementaryData? = null,
)