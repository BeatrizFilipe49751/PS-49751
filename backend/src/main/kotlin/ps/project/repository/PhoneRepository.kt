package ps.project.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ps.project.domain.User
import ps.project.domain.contact.Phone

@Repository
interface PhoneRepository: JpaRepository<Phone, Int> {
    fun deleteByUser(user: User)
}