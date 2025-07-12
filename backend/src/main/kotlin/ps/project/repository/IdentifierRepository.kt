package ps.project.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ps.project.domain.identifier.Identifier

@Repository
interface IdentifierRepository : JpaRepository<Identifier, Int>