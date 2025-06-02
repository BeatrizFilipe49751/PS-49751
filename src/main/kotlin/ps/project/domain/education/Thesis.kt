package ps.project.domain.education

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "Thesis")
data class Thesis(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @OneToOne
    @JoinColumn(name = "education_id", nullable = false)
    @JsonBackReference
    val education: Education,

    @Column(nullable = false)
    val title: String,

    @OneToMany(mappedBy = "thesis", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val supervisors: List<Supervisor> = emptyList()
)