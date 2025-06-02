package ps.project.domain.education

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.hibernate.annotations.ColumnTransformer

@Entity
@Table(name = "Supervisor")
data class Supervisor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "thesis_id", nullable = false)
    @JsonBackReference
    val thesis: Thesis,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnTransformer(write = "?::supervisor_role")
    val role: SupervisorRole
)
