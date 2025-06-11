package ps.project.mapping.mappers.lattes

import org.springframework.stereotype.Component
import ps.project.domain.contact.*
import ps.project.mapping.xml.lattes.GeneralData
import ps.project.mapping.xml.lattes.address.LattesAddressDetailsModel

@Component
class LattesContactsMapper {
    fun extractContacts(data: GeneralData?): ContactDTO {
        val dataAddresses = data?.addresses ?: return ContactDTO()

        val addresses = mutableListOf<AddressDTO>()
        val emails = mutableListOf<EmailDTO>()
        val phones = mutableListOf<PhoneDTO>()
        val websites = mutableListOf<WebsiteDTO>()

        dataAddresses.forEach { address ->
            address.professional?.let { extractContactDetails(it, ContactType.Professional, addresses, emails, phones, websites) }
            address.residential?.let { extractContactDetails(it, ContactType.Personal, addresses, emails, phones, websites) }
        }

        return ContactDTO(
            emails = emails,
            addresses = addresses,
            phones = phones,
            websites = websites
        )
    }

    private fun extractContactDetails(
        details: LattesAddressDetailsModel,
        type: ContactType,
        addresses: MutableList<AddressDTO>,
        emails: MutableList<EmailDTO>,
        phones: MutableList<PhoneDTO>,
        websites: MutableList<WebsiteDTO>
    ) {
        val address = toAddressDTO(details, type)
        if (address != null) {
            addresses.add(address)
        }
        details.email?.let { emails.add(EmailDTO(type = type, address = it)) }
        details.phone?.toIntOrNull()?.let { phones.add(PhoneDTO(type = type, number = it, device = PhoneDevice.Telephone)) }
        details.fax?.toIntOrNull()?.let { phones.add(PhoneDTO(type = type, number = it, device = PhoneDevice.Fax)) }
        details.homepage?.let { websites.add(WebsiteDTO(type = type.toWebsiteType(), url = it)) }
    }

    private fun toAddressDTO(details: LattesAddressDetailsModel, type: ContactType): AddressDTO? {
        val address = details.streetComplement ?: details.street ?: return null
        return AddressDTO(
            type = type,
            address = address,
            zipCode = details.zipCode,
            locality = details.neighborhood,
            municipality = details.city,
            country = details.country
        )
    }

    private fun ContactType.toWebsiteType(): WebsiteType {
        return when (this) {
            ContactType.Professional -> WebsiteType.Professional
            ContactType.Personal -> WebsiteType.Personal
        }
    }
}