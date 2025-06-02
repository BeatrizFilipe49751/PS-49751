package ps.project.domain.auth

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import ps.project.domain.Cv
import ps.project.domain.identifier.Identifier

@Entity
@Table(name = "Users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(unique = true)
    val cienciaID: String,

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val cv: Cv? = null
) {
    constructor() : this(0, "", "", "", "")
}
