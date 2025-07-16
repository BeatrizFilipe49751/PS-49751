package ps.project.domain.contact

import ps.project.domain.User

data class AddressDTO(
    val id: Int? = null,
    val type: ContactType,
    val address: String,
    val zipCode: String,
    val locality: String,
    val municipality: String,
    val country: String
) {
    fun toEntity(user: User) = Address (
        id = id ?: 0,
        user = user,
        type = type.name,
        address = address,
        zipCode = zipCode,
        locality = locality,
        municipality = municipality,
        country = country
    )
}
