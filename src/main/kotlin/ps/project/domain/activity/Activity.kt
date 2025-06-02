package ps.project.domain.activity

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import ps.project.domain.Cv
import java.time.LocalDate

@Entity
@Table(name = "Activity")
data class Activity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "cv_id")
    @JsonBackReference
    val cv: Cv,

    @Column(nullable = false)
    val type: String,

    @Column(nullable = true, columnDefinition = "TEXT")
    val description: String?,

    @Column(name ="start_date", nullable = false)
    val startDate: LocalDate = LocalDate.MIN,

    @Column(name ="end_date", nullable = true)
    val endDate: LocalDate?
)