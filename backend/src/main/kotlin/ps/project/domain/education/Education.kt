package ps.project.domain.education

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.hibernate.annotations.ColumnTransformer
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

    @Enumerated(EnumType.STRING)
    @Column(name = "degree", nullable = false)
    @ColumnTransformer(write = "?::education_degree")
    val degree: EducationDegree,

    @Column(name = "course", nullable = false)
    val course: String = "",

    @Column(name = "institution", nullable = false)
    val institution: String = "",

    @Column(name = "classification", nullable = true)
    val classification: String?,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @ColumnTransformer(write = "?::education_status")
    val status: EducationStatus,

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