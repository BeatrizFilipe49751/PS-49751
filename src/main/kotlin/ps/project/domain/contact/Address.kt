package ps.project.domain.contact

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.hibernate.annotations.ColumnTransformer
import ps.project.domain.Cv

@Entity
@Table(name = "Address")
data class Address(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "cv_id", nullable = false)
    @JsonBackReference
    val cv: Cv,

    @Enumerated(EnumType.STRING)
    @Column(name= "type", nullable = false)
    @ColumnTransformer(write = "?::contact_type")
    val type: ContactType,

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
