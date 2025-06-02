package ps.project.domain.profExp

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import ps.project.domain.Cv
import java.time.LocalDate

@Entity
@Table(name = "ProfessionalExperience")
data class ProfessionalExperience(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "cv_id", nullable = false)
    @JsonBackReference
    val cv: Cv,

    @Column(nullable = false)
    val institution: String,

    @Column(nullable = false)
    val role: String,

    @Column(nullable = true, columnDefinition = "TEXT")
    val description: String?,

    @Column(name ="start_date", nullable = false)
    val startDate: LocalDate = LocalDate.MIN,

    @Column(name ="end_date", nullable = true)
    val endDate: LocalDate?
)