package ps.project.domain

import ps.project.domain.activity.ActivityDTO
import ps.project.domain.author.AuthorDTO
import ps.project.domain.contact.AddressDTO
import ps.project.domain.contact.EmailDTO
import ps.project.domain.contact.PhoneDTO
import ps.project.domain.contact.WebsiteDTO
import ps.project.domain.distinction.DistinctionDTO
import ps.project.domain.education.EducationDTO
import ps.project.domain.identifier.IdentifierDTO
import ps.project.domain.language.LanguageDTO
import ps.project.domain.production.ProductionDTO
import ps.project.domain.profExp.ProfessionalExperienceDTO
import ps.project.domain.project.ProjectDTO

data class CvDTO(
    val summary: String? = null,
    val identifiers: List<IdentifierDTO> = emptyList(),
    val languages: List<LanguageDTO> = emptyList(),
    val distinctions: List<DistinctionDTO> = emptyList(),
    val educations: List<EducationDTO> = emptyList(),
    val addresses: List<AddressDTO> = emptyList(),
    val emails: List<EmailDTO> = emptyList(),
    val phones: List<PhoneDTO> = emptyList(),
    val websites: List<WebsiteDTO> = emptyList(),
    val projects: List<ProjectDTO> = emptyList(),
    val productions: List<ProductionDTO> = emptyList(),
    val profExp: List<ProfessionalExperienceDTO> = emptyList(),
    val activities: List<ActivityDTO> = emptyList(),
    val authors: List<AuthorDTO> = emptyList()
)
