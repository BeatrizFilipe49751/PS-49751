package ps.project.domain.activity

import jakarta.persistence.Entity
import jakarta.persistence.Table
import ps.project.domain.User
import java.time.LocalDate

@Entity
@Table(name = "ACADEMIC_JURY")
class AcademicJury(
    user: User,
    title: String,
    date: LocalDate,

    val candidate: String,
    val institution: String? = null,
    val course: String? = null,
    val degree: String
) : Activity(user = user, title = title, date = date)