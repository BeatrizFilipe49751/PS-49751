package ps.project.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ps.project.domain.User
import ps.project.domain.author.Author

@Repository
interface AuthorRepository: JpaRepository<Author, Int> {
    fun findByCitationName(citationName: String): Author?
    fun deleteByUser(user: User)
}