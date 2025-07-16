package ps.project.domain.production

import jakarta.persistence.Entity
import jakarta.persistence.Table
import ps.project.domain.User
import java.time.LocalDate

@Entity
@Table(name = "WEBSITE_PROD")
class WebsiteProd(
    id: Int,
    title: String,
    date: LocalDate,
    user: User,

    val url: String?,
    val description: String?
) : Production(id = id, title = title, date = date, user = user)
