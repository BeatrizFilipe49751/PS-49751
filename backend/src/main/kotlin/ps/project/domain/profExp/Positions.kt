package ps.project.domain.profExp

import jakarta.persistence.Entity
import ps.project.domain.User
import java.time.LocalDate

@Entity
class Positions (
    user: User,
    institution: String,
    description: String? = null,
    startDate: LocalDate = LocalDate.MIN,
    endDate: LocalDate? = null
): ProfessionalExperience(
    user = user, institution = institution, description = description, startDate = startDate, endDate = endDate
)