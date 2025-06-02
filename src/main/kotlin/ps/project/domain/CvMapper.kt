package ps.project.domain

import org.w3c.dom.Document
import ps.project.domain.activity.ActivityDTO
import ps.project.domain.author.Author
import ps.project.domain.author.AuthorDTO
import ps.project.domain.contact.ContactDTO
import ps.project.domain.distinction.DistinctionDTO
import ps.project.domain.education.EducationDTO
import ps.project.domain.identifier.IdentifierDTO
import ps.project.domain.language.LanguageDTO
import ps.project.domain.production.ProductionDTO
import ps.project.domain.profExp.ProfessionalExperienceDTO
import ps.project.domain.project.ProjectDTO

interface CvMapper {
    fun supports(source: String): Boolean
    fun extractCv(xmlString: String): CvDTO
}