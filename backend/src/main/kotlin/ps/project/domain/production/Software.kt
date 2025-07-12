package ps.project.domain.production

import jakarta.persistence.Entity
import jakarta.persistence.Table
import ps.project.domain.User
import java.time.LocalDate

@Entity
@Table(name = "SOFTWARE")
class Software(
    title: String,
    date: LocalDate,
    user: User,

    val description: String?,
    val version: String?,
    val platform: String?,
    val doi: String?
) : Production(title = title, date = date, user = user)
