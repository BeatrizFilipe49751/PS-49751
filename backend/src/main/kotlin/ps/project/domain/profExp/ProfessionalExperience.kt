package ps.project.domain.profExp

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import ps.project.domain.User
import java.time.LocalDate

@Entity
@Table(name = "Professional_Experience")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
open class ProfessionalExperience(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    val user: User,

    @Column(nullable = false)
    val institution: String,

    @Column(nullable = true, columnDefinition = "TEXT")
    val description: String?,

    @Column(name ="start_date", nullable = false)
    val startDate: LocalDate = LocalDate.MIN,

    @Column(name ="end_date", nullable = true)
    val endDate: LocalDate?
)