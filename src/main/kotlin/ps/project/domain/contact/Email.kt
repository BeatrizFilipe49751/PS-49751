package ps.project.domain.contact

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.hibernate.annotations.ColumnTransformer
import ps.project.domain.Cv

@Entity
@Table(name = "Email")
data class Email(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "cv_id", nullable = false)
    @JsonBackReference
    val cv: Cv,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnTransformer(write = "?::contact_type")
    val type: ContactType,

    @Column(nullable = false)
    val address: String
)
