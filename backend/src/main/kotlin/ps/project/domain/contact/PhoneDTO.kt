package ps.project.domain.contact

import ps.project.domain.User

data class PhoneDTO(
    val id: Int? = null,
    val type: ContactType,
    val number: Int,
    val countryCode: Int? = null,
    val device: PhoneDevice,
) {
    fun toEntity(user: User) = Phone (
        user = user,
        type = type.name,
        number = number,
        countryCode = countryCode,
        device = device.name
    )
}
