package ps.project.domain.production

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import ps.project.domain.User
import java.time.LocalDate

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
open class Production(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    val user: User,

    @Column(name = "title", nullable = false)
    val title: String = "",

    @Column(name = "date", nullable = false)
    val date: LocalDate = LocalDate.MIN
)