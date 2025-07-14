package ps.project.domain.profExp

enum class ProfExpType(val code: String, private val customValue: String? = null) {
    Science("T01"),
    TeachingHE("T02", "Teaching in Higher Education"),
    Positions("T04", "Positions / Appointments"),
    Others("T00");

    val value: String
        get() = customValue ?: name
}