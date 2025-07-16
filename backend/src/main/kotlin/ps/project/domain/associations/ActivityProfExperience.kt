package ps.project.domain.associations

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import ps.project.domain.activity.Activity
import ps.project.domain.profExp.ProfessionalExperience

@Entity
@IdClass(ActivityProfExperienceId::class)
@Table(name = "activity_professionalexperience")
data class ActivityProfExperience(
    @Id
    @ManyToOne
    @JoinColumn(name = "activity_id")
    val activity: Activity,

    @Id
    @ManyToOne
    @JoinColumn(name = "experience_id")
    val profExperience: ProfessionalExperience
)
