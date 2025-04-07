package ps.project.domain

import jakarta.persistence.*
import org.springframework.data.annotation.Id

@Entity
@Table(name = "CV")
data class Cv (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    val user: User,

    @Column(columnDefinition = "TEXT")
    val summary: String = "",

    @OneToMany(mappedBy = "cv", cascade = [CascadeType.ALL], orphanRemoval = true)
    val language: List<Language> = emptyList(),

    /*@OneToMany(mappedBy = "cv", cascade = [CascadeType.ALL], orphanRemoval = true)
    val education: List<Education> = emptyList(),*/
)