package ps.project.domain.contact

enum class PhoneDevice(val code: Int, private val customValue: String? = null) {
    Telephone(1),
    Mobile(2, "Mobile phone"),
    Fax(3),
    Pager(4);

    val value: String
        get() = customValue ?: name
}