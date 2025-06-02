package ps.project.domain.author

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import ps.project.domain.Cv

@Entity
@Table(name = "Author")
data class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "cv_id", nullable = true)
    @JsonBackReference
    val cv: Cv? = null,

    @Column(name = "citation_name", nullable = false)
    val citationName: String,
)
