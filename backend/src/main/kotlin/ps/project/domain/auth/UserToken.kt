package ps.project.domain.auth

import jakarta.persistence.*
import ps.project.domain.User
import java.time.LocalDateTime

@Entity
@Table(name = "usertoken")
data class UserToken(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(nullable = false, unique = true)
    val token: String,

    @Column(nullable = false, name = "issued_at")
    val issuedAt: LocalDateTime,

    @Column(nullable = false, name = "expires_at")
    val expiresAt: LocalDateTime
)

