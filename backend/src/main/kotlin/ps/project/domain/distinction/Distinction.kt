package ps.project.domain.distinction

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import ps.project.domain.User

@Entity
@Table(name = "Distinction")
data class Distinction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    val user: User,

    @Column(nullable = false)
    val type: String,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, name = "\"year\"")
    val year: Int,

    @Column(name = "promoting_entity", nullable = true)
    val promotingEntity: String?
)