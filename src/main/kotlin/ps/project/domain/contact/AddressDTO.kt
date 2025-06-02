package ps.project.domain.contact

import ps.project.domain.Cv

data class AddressDTO(
    val id: Int? = null,
    val type: ContactType,
    val address: String,
    val zipCode: String,
    val locality: String,
    val municipality: String,
    val country: String
) {
    fun toEntity(cv: Cv) = Address (
        cv = cv,
        type = type,
        address = address,
        zipCode = zipCode,
        locality = locality,
        municipality = municipality,
        country = country
    )
}
