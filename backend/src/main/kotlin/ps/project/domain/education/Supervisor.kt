package ps.project.domain.education

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import ps.project.domain.production.Thesis

@Entity
@Table(name = "Supervisor")
data class Supervisor(
    @EmbeddedId
    val id: SupervisorId = SupervisorId(),

    @Column(nullable = false)
    val role: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("thesisId")
    @JoinColumn(name = "thesis_id", nullable = false)
    @JsonBackReference
    val thesis: Thesis
)
