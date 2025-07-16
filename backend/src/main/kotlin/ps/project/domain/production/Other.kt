package ps.project.domain.production

import jakarta.persistence.Entity
import jakarta.persistence.Table
import ps.project.domain.User
import java.time.LocalDate

@Entity
@Table(name = "OTHER")
class Other(
    id: Int,
    title: String,
    date: LocalDate,
    user: User,

    val description: String?,
    val url: String?
) : Production(id = id, title = title, date = date, user = user)
