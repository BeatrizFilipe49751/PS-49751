package ps.project.domain.production

import jakarta.persistence.Entity
import jakarta.persistence.Table
import ps.project.domain.User
import java.time.LocalDate

@Entity
@Table(name = "PATENT")
class Patent(
    title: String,
    date: LocalDate,
    user: User
) : Production(title = title, date = date, user = user)
