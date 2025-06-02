package ps.project.domain.distinction

enum class DistinctionType(val code: String, private val customValue: String? = null) {
    Award("P"),
    Title("T"),
    Other("O", "Other distinction");

    val value: String
        get() = customValue ?: name
}