package ps.project.domain.identifier

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import ps.project.domain.User

@Entity
@Table(name = "Identifier")
data class Identifier(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    val id: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    val user: User,

    @Column(name = "type", nullable = false)
    val type: String
)
