package ps.project.domain.language

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.hibernate.annotations.ColumnTransformer
import ps.project.domain.User

@Entity
@Table(name = "Language")
data class Language(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    val user: User,

    @Column(nullable = false)
    val language: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    @ColumnTransformer(write = "?::language_level")
    val comprehension: LanguageLevel? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    @ColumnTransformer(write = "?::language_level")
    val reading: LanguageLevel? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    @ColumnTransformer(write = "?::language_level")
    val speaking: LanguageLevel? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    @ColumnTransformer(write = "?::language_level")
    val writing: LanguageLevel? = null,
)