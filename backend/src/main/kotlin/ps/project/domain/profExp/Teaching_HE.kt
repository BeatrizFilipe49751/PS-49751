package ps.project.domain.profExp

import jakarta.persistence.Entity
import jakarta.persistence.Table
import ps.project.domain.User
import java.time.LocalDate

@Entity
@Table(name = "Teaching_HE")
class TeachingHE (
    user: User,
    institution: String,
    description: String? = null,
    startDate: LocalDate = LocalDate.MIN,
    endDate: LocalDate? = null
): ProfessionalExperience(
    user = user, institution = institution, description = description, startDate = startDate, endDate = endDate
)