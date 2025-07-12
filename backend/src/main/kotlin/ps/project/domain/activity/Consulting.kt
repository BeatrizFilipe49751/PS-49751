package ps.project.domain.activity

import jakarta.persistence.Entity
import ps.project.domain.User
import java.time.LocalDate

@Entity
class Consulting(
    user: User,
    title: String,
    date: LocalDate,

    val institution: String? = null,
) : Activity(user = user, title = title, date = date)