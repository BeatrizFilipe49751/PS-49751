package ps.project.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ps.project.domain.User
import ps.project.domain.associations.ActivityProfExperience
import ps.project.domain.associations.ActivityProfExperienceId

@Repository
interface ActivityProfExperienceRepository: JpaRepository<ActivityProfExperience, ActivityProfExperienceId>