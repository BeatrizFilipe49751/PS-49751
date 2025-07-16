package ps.project.domain.activity

import jakarta.persistence.Entity
import ps.project.domain.User
import java.time.LocalDate

@Entity
class Supervision(
    id: Int,
    user: User,
    title: String,
    date: LocalDate,

    val role: String,
    val supervisee: String,
    val institution: String? = null,
    val course: String? = null,
    val courseCode: String? = null,
    val degree: String,
) : Activity(id = id, user = user, title = title, date = date)