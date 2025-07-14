package ps.project.domain.language

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import ps.project.domain.User

@Entity
@Table(name = "Language")
data class Language(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    val user: User,

    @Column(nullable = false)
    val language: String,

    @Column(nullable = true)
    val comprehension: String? = null,

    @Column(nullable = true)
    val reading: String? = null,

    @Column(nullable = true)
    val speaking: String? = null,

    @Column(nullable = true)
    val writing: String? = null,
)