package ps.project.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ps.project.domain.education.Supervisor

@Repository
interface SupervisorRepository : JpaRepository<Supervisor, Int>