package ps.project.domain.contact

import ps.project.domain.Cv

data class EmailDTO(
    val id: Int? = null,
    val type: ContactType,
    val address: String
) {
    fun toEntity(cv: Cv) = Email (
        cv = cv,
        type = type,
        address = address
    )
}
