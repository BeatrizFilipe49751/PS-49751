package ps.project.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ps.project.domain.User
import ps.project.domain.contact.Email

@Repository
interface EmailRepository: JpaRepository<Email, Int> {
    fun deleteByUser(user: User)
}