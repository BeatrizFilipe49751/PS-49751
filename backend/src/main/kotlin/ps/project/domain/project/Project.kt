package ps.project.domain.project

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.hibernate.annotations.ColumnTransformer
import ps.project.domain.User
import java.time.LocalDate

@Entity
@Table(name = "Project")
data class Project(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    val user: User,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = true)
    val institution: String?,

    @Column(name = "start_date", nullable = false)
    val startDate: LocalDate,

    @Column(name = "end_date", nullable = true)
    val endDate: LocalDate?,

    @Column(nullable = true)
    val description: String?,

    @Column(name = "funding_type", nullable = false)
    val fundingType: String,

    @Column(nullable = true)
    val identifier: String?,

    @Column(nullable = true)
    val role: String?,

    @Column(nullable = true)
    val state: String?,
)
