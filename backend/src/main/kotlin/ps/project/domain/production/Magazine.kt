package ps.project.domain.production

import jakarta.persistence.Entity
import jakarta.persistence.Table
import ps.project.domain.User
import java.time.LocalDate

@Entity
@Table(name = "MAGAZINE")
class Magazine(
    title: String,
    date: LocalDate,
    user: User,
    doi: String?,
    pageFrom: String?,
    pageTo: String?,
    secondaryTitle: String?,
    volume: String?,
    url: String?,
    val issn: String?
) : Article(title, date, user, doi, pageFrom, pageTo, secondaryTitle, volume, url)