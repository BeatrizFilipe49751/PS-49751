package ps.project.domain.production

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.hibernate.annotations.ColumnTransformer
import ps.project.domain.Cv
import java.time.LocalDate

@Entity
@Table(name = "Production")
data class Production(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "cv_id")
    @JsonBackReference
    val cv: Cv,

    @Column(name = "title", nullable = false)
    val title: String = "",

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnTransformer(write = "?::production_type")
    val type: ProductionType,

    @Column(name = "date", nullable = false)
    val date: LocalDate = LocalDate.MIN,

    @Column(name = "description", nullable = true)
    val description: String? = null,

    @Column(name= "doi", nullable = true)
    val doi: String? = null,

    @Column(name= "issn", nullable = true)
    val issn: String? = null,

    @Column(name= "isbn", nullable = true)
    val isbn: String? = null,

    @Column(name= "url", nullable = true)
    val url: String? = null,

    @Column(name= "secondary_title", nullable = true)
    val secondaryTitle: String? = null,

    @Column(name= "volume", nullable = true)
    val volume: Int? = null,

    @Column(name= "version", nullable = true)
    val version: String? = null,

    @Column(name= "platform", nullable = true)
    val platform: String? = null,

    @Column(name= "page_from", nullable = true)
    val pageFrom: Int? = null,

    @Column(name= "page_to", nullable = true)
    val pageTo: Int? = null,
)