package ps.project.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ps.project.domain.auth.UserToken
import java.util.*

@Repository
interface UserTokenRepository: JpaRepository<UserToken, Int> {
    fun findByToken(token: String): Optional<UserToken>
    fun deleteByToken(token: String)
}