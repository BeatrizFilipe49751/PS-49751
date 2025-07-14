package ps.project.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ps.project.domain.User
import ps.project.domain.associations.ProjectAuthor
import ps.project.domain.associations.ProjectAuthorId
import ps.project.domain.project.Project

@Repository
interface ProjectAuthorRepository: JpaRepository<ProjectAuthor, ProjectAuthorId> {
    fun findByProject(project: Project): List<ProjectAuthor>
}