package ps.project.domain

import jakarta.persistence.*
import org.springframework.data.annotation.Id

@Entity
@Table(name = "Language")
data class Language(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "cv_id", nullable = false)
    val cv: Cv,

    @Column
    val language: String,

    @Column
    val comprehension: Int = 0,

    @Column
    val reading: Int = 0,

    @Column
    val speaking: Int = 0,

    @Column
    val writing: Int = 0
)
