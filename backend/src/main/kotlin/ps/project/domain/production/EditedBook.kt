package ps.project.domain.production

import jakarta.persistence.Entity
import jakarta.persistence.Table
import ps.project.domain.User
import java.time.LocalDate

@Entity
@Table(name = "EDITED_BOOK")
class EditedBook(
    title: String,
    date: LocalDate,
    user: User,

    val doi: String?,
    val isbn: String?,
    val volume: String?,
    val url: String?
) : Production(title = title, date = date, user = user)
