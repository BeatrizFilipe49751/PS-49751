package ps.project.domain.production

import jakarta.persistence.Entity
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.Table
import ps.project.domain.User
import java.time.LocalDate

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "ARTICLE")
open class Article(
    id: Int,
    title: String,
    date: LocalDate,
    user: User,

    val doi: String?,
    val pageFrom: String?,
    val pageTo: String?,
    val secondaryTitle: String?,
    val volume: String?,
    val url: String?
) : Production(id = id, title = title, date = date, user = user)
