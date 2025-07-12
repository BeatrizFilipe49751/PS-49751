package ps.project.domain.author

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import ps.project.domain.User

@Entity
@Table(name = "Author")
data class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    val user: User,

    @Column(name = "citation_name", nullable = false)
    val citationName: String,
)
