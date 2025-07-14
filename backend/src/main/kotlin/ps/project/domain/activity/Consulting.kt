package ps.project.domain.activity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import ps.project.domain.User
import java.time.LocalDate

@Entity
class Consulting(
    user: User,
    title: String,
    date: LocalDate,

    @Column(name = "end_date")
    val endDate: LocalDate? = null,
) : Activity(user = user, title = title, date = date)