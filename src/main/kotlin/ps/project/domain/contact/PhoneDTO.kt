package ps.project.domain.contact

import ps.project.domain.Cv

data class PhoneDTO(
    val id: Int? = null,
    val type: ContactType,
    val number: Int,
    val countryCode: Int? = null,
    val device: PhoneDevice,
) {
    fun toEntity(cv: Cv) = Phone (
        cv = cv,
        type = type,
        number = number,
        countryCode = countryCode,
        device = device
    )
}
