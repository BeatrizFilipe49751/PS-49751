package ps.project.domain.education

enum class EducationDegree(val code: String, val value: String) {
    PrimaryEducation("L", "Ensino básico 3º ciclo"),
    SecondaryEducation("M", "Ensino secundário"),
    Bachelor("2", "Licenciatura"),
    Postgraduate("R", "Pós-Graduação"),
    Master("3", "Mestrado"),
    Doctorate("5", "Doutoramento"),
    PostDoctorate("A", "Pós-doutoramento"),
    Other("E", "Outros");

    companion object {
        fun fromInt(value: Int): EducationDegree = when (value) {
            1 -> PrimaryEducation
            2 -> SecondaryEducation
            3 -> Bachelor
            4 -> Postgraduate
            5 -> Master
            6 -> Doctorate
            7 -> PostDoctorate
            else -> Other
        }
    }
}