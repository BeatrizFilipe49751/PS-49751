package ps.project.domain.associations

import java.io.Serializable

data class ProductionAuthorId(
    val production: Int = 0,
    val author: Int = 0
) : Serializable
