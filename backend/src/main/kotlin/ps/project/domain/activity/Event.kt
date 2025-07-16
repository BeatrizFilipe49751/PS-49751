package ps.project.domain.activity

import jakarta.persistence.Entity
import ps.project.domain.User
import java.time.LocalDate

@Entity
class Event(
    id: Int,
    user: User,
    title: String,
    date: LocalDate,

    val institution: String? = null,
) : Activity(id = id, user = user, title = title, date = date)