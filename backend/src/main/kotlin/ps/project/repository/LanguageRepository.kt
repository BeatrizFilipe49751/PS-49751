package ps.project.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ps.project.domain.User
import ps.project.domain.language.Language

@Repository
interface LanguageRepository : JpaRepository<Language, Int> {
    fun deleteByUser(user: User)
}