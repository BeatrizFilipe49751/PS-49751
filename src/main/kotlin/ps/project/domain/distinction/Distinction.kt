package ps.project.domain.distinction

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.hibernate.annotations.ColumnTransformer
import ps.project.domain.Cv

@Entity
@Table(name = "Distinction")
data class Distinction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "cv_id", nullable = false)
    @JsonBackReference
    val cv: Cv,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnTransformer(write = "?::distinction_type")
    val type: DistinctionType,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val year: Int,

    @Column(name = "promoting_entity", nullable = true)
    val promotingEntity: String?
)