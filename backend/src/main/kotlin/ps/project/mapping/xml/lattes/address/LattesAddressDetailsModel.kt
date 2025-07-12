package ps.project.mapping.xml.lattes.address

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class LattesAddressDetailsModel(
    @JacksonXmlProperty(isAttribute = true, localName = "LOGRADOURO")
    val street: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "LOGRADOURO-COMPLEMENTO")
    val streetComplement: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "CEP")
    val zipCode: String,

    @JacksonXmlProperty(isAttribute = true, localName = "BAIRRO")
    val neighborhood: String,

    @JacksonXmlProperty(isAttribute = true, localName = "CIDADE")
    val city: String,

    @JacksonXmlProperty(isAttribute = true, localName = "PAIS")
    val country: String,

    @JacksonXmlProperty(isAttribute = true, localName = "E-MAIL")
    val email: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "TELEFONE")
    val phone: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "FAX")
    val fax: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "HOME-PAGE")
    val homepage: String? = null
)
