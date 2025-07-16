package ps.project.domain.production

import jakarta.persistence.Entity
import jakarta.persistence.Table
import ps.project.domain.User
import java.time.LocalDate

@Entity
@Table(name = "BOOK_CHAPTER")
class BookChapter(
    id: Int,
    title: String,
    date: LocalDate,
    user: User,
    doi: String?,
    pageFrom: String?,
    pageTo: String?,
    secondaryTitle: String?,
    volume: String?,
    url: String?,
    val isbn: String?
) : Article(id, title, date, user, doi, pageFrom, pageTo, secondaryTitle, volume, url)