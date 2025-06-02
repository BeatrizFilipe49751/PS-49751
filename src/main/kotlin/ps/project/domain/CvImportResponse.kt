package ps.project.domain

import ps.project.domain.activity.Activity
import ps.project.domain.author.Author
import ps.project.domain.contact.Address
import ps.project.domain.contact.Email
import ps.project.domain.contact.Phone
import ps.project.domain.contact.Website
import ps.project.domain.distinction.Distinction
import ps.project.domain.education.Education
import ps.project.domain.identifier.Identifier
import ps.project.domain.language.Language
import ps.project.domain.production.Production
import ps.project.domain.profExp.ProfessionalExperience
import ps.project.domain.project.Project

data class CvImportResponse(
    val message: String,
    val id: Int,
    val summary: String?,
    val identifiers: List<Identifier>,
    val languages: List<Language>,
    val distinctions: List<Distinction>,
    val addresses: List<Address>,
    val emails: List<Email>,
    val phones: List<Phone>,
    val websites: List<Website>,
    val educations: List<Education>,
    val projects: List<Project>,
    val productions: List<Production>,
    val profExp: List<ProfessionalExperience>,
    val activities: List<Activity>,
    val authors: List<Author>
)
