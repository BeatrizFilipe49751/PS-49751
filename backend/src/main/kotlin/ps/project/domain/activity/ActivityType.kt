package ps.project.domain.activity

enum class ActivityType(val code: String, private val customValue: String? = null) {
    Consulting("S101"),
    Event("S201", "Event organisation"),
    Supervision("S110"),
    AcademicJury("S105", "Jury of academic degree"),
    SubjectTaught("S204", "Course / Discipline taught");

    val value: String
        get() = customValue ?: name
}