package ps.project.mapping.xml.lattes.address

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesAddressModel(
    @JacksonXmlProperty(localName = "ENDERECO-PROFISSIONAL")
    val professional: LattesAddressDetailsModel? = null,

    @JacksonXmlProperty(localName = "ENDERECO-RESIDENCIAL")
    val residential: LattesAddressDetailsModel? = null
)
