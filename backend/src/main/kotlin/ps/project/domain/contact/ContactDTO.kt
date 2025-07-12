package ps.project.domain.contact

data class ContactDTO(
    val emails: List<EmailDTO> = emptyList(),
    val websites: List<WebsiteDTO> = emptyList(),
    val phones: List<PhoneDTO> = emptyList(),
    val addresses: List<AddressDTO> = emptyList()
)
