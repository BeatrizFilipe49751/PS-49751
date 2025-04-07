package ps.project.domain

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "Users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val birthdate: LocalDate,

    @Column(nullable = false)
    val gender: String,

    @Column(unique = true)
    val cienciaID: String
) {
    constructor() : this(0, "", "", "", LocalDate.MIN, "", "")
}
