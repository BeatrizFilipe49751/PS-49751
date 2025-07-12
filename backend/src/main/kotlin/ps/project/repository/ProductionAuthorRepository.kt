package ps.project.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ps.project.domain.associations.ProductionAuthor
import ps.project.domain.associations.ProductionAuthorId
import ps.project.domain.production.Production

@Repository
interface ProductionAuthorRepository: JpaRepository<ProductionAuthor, ProductionAuthorId> {
    fun findByProduction(production: Production): List<ProductionAuthor>
}