package ps.project.domain.contact

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.hibernate.annotations.ColumnTransformer
import ps.project.domain.User

@Entity
@Table(name = "Website")
data class Website(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    val user: User,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnTransformer(write = "?::website_type")
    val type: WebsiteType,

    @Column(nullable = false)
    val url: String
)
