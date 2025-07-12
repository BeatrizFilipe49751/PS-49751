package ps.project.domain.education

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.hibernate.annotations.ColumnTransformer
import ps.project.domain.production.Thesis

@Entity
@Table(name = "Supervisor")
data class Supervisor(
    @EmbeddedId
    val id: SupervisorId = SupervisorId(),

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnTransformer(write = "?::supervisor_role")
    val role: SupervisorRole,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("thesisId")
    @JoinColumn(name = "thesis_id", nullable = false)
    @JsonBackReference
    val thesis: Thesis
)
