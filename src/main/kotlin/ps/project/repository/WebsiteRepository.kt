package ps.project.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ps.project.domain.contact.Website

@Repository
interface WebsiteRepository: JpaRepository<Website, Int>