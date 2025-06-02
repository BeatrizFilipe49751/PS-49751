package ps.project.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ps.project.domain.profExp.ProfessionalExperience

@Repository
interface ProfExpRepository : JpaRepository<ProfessionalExperience, Int>