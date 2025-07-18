package ps.project.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ps.project.domain.User
import ps.project.domain.production.Production

@Repository
interface ProductionRepository : JpaRepository<Production, Int> {
    fun findAllByUser(user: User): List<Production>
    fun deleteByUser(user: User)
}