package ps.project.domain.production

import jakarta.persistence.Entity
import jakarta.persistence.Table
import ps.project.domain.User
import java.time.LocalDate

@Entity
@Table(name = "RESEARCH_TECHNIQUE")
class ResearchTechnique(
    id: Int,
    title: String,
    date: LocalDate,
    user: User,

    val doi: String?
) : Production(id = id, title = title, date = date, user = user)
