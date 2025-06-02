package ps.project.domain.associations

import jakarta.persistence.*
import ps.project.domain.author.Author
import ps.project.domain.production.Production

@Entity
@IdClass(ProductionAuthorId::class)
@Table(name = "Production_Author")
data class ProductionAuthor(
    @Id
    @ManyToOne
    @JoinColumn(name = "production_id")
    val production: Production,

    @Id
    @ManyToOne
    @JoinColumn(name = "author_id")
    val author: Author
)
