package ps.project.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ps.project.domain.Cv
import java.util.*

@Repository
interface CvRepository: JpaRepository<Cv, Int> {

    // Retrieve CV by user ID
    fun findByUserId(userId: Int): Optional<Cv>
}