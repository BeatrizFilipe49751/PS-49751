package ps.project.mapping.xml.cienciaVitae.contacts

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import ps.project.mapping.xml.cienciaVitae.common.CienciaVitaeXmlPayload
import ps.project.mapping.xml.cienciaVitae.common.CodeValueXml

@JacksonXmlRootElement(localName = "mailing-address", namespace = "http://www.cienciavitae.pt/ns/mailing-address")
data class AddressXml(
    val addressType: CodeValueXml,

    @field:JacksonXmlProperty(localName = "street-address")
    val streetAddress: String? = null,

    @field:JacksonXmlProperty(localName = "postal-code")
    val postalCode: String? = null,

    @field:JacksonXmlProperty(localName = "city")
    val city: String? = null,

    @field:JacksonXmlProperty(localName = "province-state")
    val provinceState: String? = null,

    val country: CodeValueXml
) : CienciaVitaeXmlPayload()
