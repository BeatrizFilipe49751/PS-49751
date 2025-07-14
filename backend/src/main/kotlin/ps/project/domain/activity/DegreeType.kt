package ps.project.domain.activity

enum class DegreeType(val code: String, private val customValue: String? = null) {
    MASTERS("M", "Master"),
    DOCTORATE("D", "PhD"),
    POSTDOC(""),
    SPECIALIZATION("CA", "Specialization course"),
    RESEARCH("IC", "Scientific initiation"),
    BACHELOR("LB", "Degree"),
    OTHER("O", "Other");

    val value: String
        get() = customValue ?: name
}