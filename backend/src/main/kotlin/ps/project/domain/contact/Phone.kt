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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnTransformer(write = "?::contact_type")
    val type: ContactType,

    @Column(nullable = false)
    val number: Int,

    @Column(name = "country_code", nullable = true)
    val countryCode: Int? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnTransformer(write = "?::phone_device")
    val device: PhoneDevice,
)
