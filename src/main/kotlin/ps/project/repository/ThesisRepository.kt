package ps.project.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ps.project.domain.education.Thesis

@Repository
interface ThesisRepository : JpaRepository<Thesis, Int>