package ps.project.domain.contact

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import ps.project.domain.User

@Entity
@Table(name = "Address")
data class Address(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    val user: User,

    @Column(name= "type", nullable = false)
    val type: String,

    @Column(name= "address", nullable = false)
    val address: String,

    @Column(name= "zip_code", nullable = false)
    val zipCode: String,

    @Column(name= "locality", nullable = false)
    val locality: String,

    @Column(name= "municipality", nullable = false)
    val municipality: String,

    @Column(name= "country", nullable = false)
    val country: String
)
