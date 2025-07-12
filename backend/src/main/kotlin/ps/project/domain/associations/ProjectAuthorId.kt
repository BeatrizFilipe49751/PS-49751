package ps.project.domain.associations

import java.io.Serializable

data class ProjectAuthorId(
    val project: Int = 0,
    val author: Int = 0
) : Serializable
