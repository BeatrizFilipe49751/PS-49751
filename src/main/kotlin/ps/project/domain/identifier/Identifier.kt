package ps.project.domain.identifier

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.hibernate.annotations.ColumnTransformer
import ps.project.domain.Cv

@Entity
@Table(name = "Identifier")
data class Identifier(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    val id: String,

    @ManyToOne
    @JoinColumn(name = "cv_id")
    @JsonBackReference
    val cv: Cv,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    @ColumnTransformer(write = "?::identifier_type")
    val type: IdentifierType
)
