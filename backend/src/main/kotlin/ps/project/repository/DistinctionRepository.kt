package ps.project.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ps.project.domain.User
import ps.project.domain.distinction.Distinction

@Repository
interface DistinctionRepository : JpaRepository<Distinction, Int>{
    fun deleteByUser(user: User)
}