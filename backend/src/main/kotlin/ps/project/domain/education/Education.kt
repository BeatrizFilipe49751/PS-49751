package ps.project.domain.education

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import ps.project.domain.User
import ps.project.domain.production.Thesis
import java.time.LocalDate

@Entity
@Table(name = "Education")
data class Education (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    val user: User,

    @Column(name = "degree", nullable = false)
    val degree: String,

    @Column(name = "course", nullable = false)
    val course: String = "",

    @Column(name = "institution", nullable = false)
    val institution: String = "",

    @Column(name = "classification", nullable = true)
    val classification: String?,

    @Column(name = "status", nullable = false)
    val status: String,

    @Column(name = "course_code", nullable = true)
    val courseCode: String?,

    @Column(name = "start_date", nullable = true)
    val startDate: LocalDate?,

    @Column(name = "end_date", nullable = false)
    val endDate: LocalDate = LocalDate.MIN,

    @OneToOne(mappedBy = "education", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val thesis: Thesis? = null
)