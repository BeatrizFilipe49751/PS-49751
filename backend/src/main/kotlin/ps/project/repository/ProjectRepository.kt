package ps.project.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ps.project.domain.User
import ps.project.domain.project.Project

@Repository
interface ProjectRepository : JpaRepository<Project, Int>{
    fun deleteByUser(user: User)
}