package ps.project.domain.contact

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.hibernate.annotations.ColumnTransformer
import ps.project.domain.User

@Entity
@Table(name = "Phone")
data class Phone(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    val user: User,

    @Column(nullable = false)
    val type: String,

    @Column(nullable = false)
    val number: Int,

    @Column(name = "country_code", nullable = true)
    val countryCode: Int? = null,

    @Column(nullable = false)
    val device: String,
)
