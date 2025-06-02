package ps.project.domain.language

enum class LanguageLevel(val code: String, private val customValue: String? = null) {
    Beginner("A1"),
    Elementary("A2"),
    Intermediate("B1"),
    UpperIntermediate("B2", "Upper intermediate"),
    Advanced("C1"),
    Proficiency("C2");

    val value: String
        get() = customValue ?: name
}