package ps.project.domain.associations

import jakarta.persistence.*
import ps.project.domain.author.Author
import ps.project.domain.project.Project

@Entity
@IdClass(ProjectAuthorId::class)
@Table(name = "Project_Author")
data class ProjectAuthor(
    @Id
    @ManyToOne
    @JoinColumn(name = "project_id")
    val project: Project,

    @Id
    @ManyToOne
    @JoinColumn(name = "author_id")
    val author: Author
)
