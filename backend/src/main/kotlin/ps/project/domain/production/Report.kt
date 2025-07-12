package ps.project.domain.production

import jakarta.persistence.Entity
import jakarta.persistence.Table
import ps.project.domain.User
import java.time.LocalDate

@Entity
@Table(name = "REPORT")
class Report(
    title: String,
    date: LocalDate,
    user: User,

    val url: String?,
    val volume: String?,
    val doi: String?
) : Production(title = title, date = date, user = user)
