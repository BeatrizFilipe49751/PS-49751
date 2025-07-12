package ps.project.domain.education

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class SupervisorId(
    @Column(name = "thesis_id")
    val thesisId: Int = 0,

    @Column(name = "name")
    val name: String = ""
) : Serializable