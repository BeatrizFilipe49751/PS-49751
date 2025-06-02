package ps.project.domain.auth

import jakarta.persistence.*
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

    @Column(nullable = false)
    val issuedAt: LocalDateTime,

    @Column(nullable = false)
    val expiresAt: LocalDateTime
){
    constructor() : this(0, User(), "", LocalDateTime.MIN, LocalDateTime.MIN)
}

