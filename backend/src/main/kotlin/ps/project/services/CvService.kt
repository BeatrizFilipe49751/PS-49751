package ps.project.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import ps.project.domain.CvDTO
import ps.project.domain.activity.Activity
import ps.project.domain.activity.ActivityDTO
import ps.project.domain.User
import ps.project.domain.activity.ActivityMapper
import ps.project.domain.author.AuthorDTO
import ps.project.domain.contact.Address
import ps.project.domain.contact.AddressDTO
import ps.project.domain.contact.EmailDTO
import ps.project.domain.contact.Phone
import ps.project.domain.contact.PhoneDTO
import ps.project.domain.contact.WebsiteDTO
import ps.project.domain.distinction.Distinction
import ps.project.domain.distinction.DistinctionDTO
import ps.project.domain.education.Education
import ps.project.domain.education.EducationDTO
import ps.project.domain.education.SupervisorDTO
import ps.project.domain.production.ThesisDTO
import ps.project.domain.identifier.IdentifierDTO
import ps.project.domain.language.Language
import ps.project.domain.language.LanguageDTO
import ps.project.domain.production.Production
import ps.project.domain.production.ProductionDTO
import ps.project.domain.production.ProductionMapper
import ps.project.domain.profExp.ProfessionalExperience
import ps.project.domain.profExp.ProfessionalExperienceDTO
import ps.project.domain.project.Project
import ps.project.domain.project.ProjectDTO
import ps.project.domain.utils.hasCvData
import ps.project.repository.*

@Service
class CvService(
    private val userRepository: UserRepository,
    private val userTokenRepository: UserTokenRepository,
    private val parser: CvParserService,
    private val exportService: CvExportService,
    private val persistenceService: CvPersistenceService
) {
    @Value("\${cienciavitae.api.base-url}")
    lateinit var cienciaVitaeApiBaseUrl: String

    fun sendToCienciaVitae(token: String) {
        val user = getUserFromToken(token)
        if (!user.hasCvData()) {
            throw IllegalArgumentException("The user does not have CV data to export.")
        }
        val baseUrl = buildBaseUrl(user.cienciaID)
        exportService.sendCvToCienciaVitae(user, baseUrl)
    }

    fun updateCv(token: String, cvDTO: CvDTO) {
    }

    @Transactional
    fun importCv(token: String, source: String, file: MultipartFile) : User {
        val user = getUserFromToken(token)
        val cvDTO = parser.parse(file, source)
        persistenceService.saveCvFromDto(cvDTO, user)
        return userRepository.findById(user.id)
            .orElseThrow { IllegalArgumentException("User not found.") }
    }

    fun getCvForUser(token: String): CvDTO {
        val user = getUserFromToken(token)
        return toDTO(user)
    }

    private fun getUserFromToken(token: String): User {
        return userTokenRepository.findByToken(token)
            .orElseThrow { IllegalArgumentException("Token inválido ou expirado.") }
            .user
    }

    private fun buildBaseUrl(cienciaID: String): String {
        return "$cienciaVitaeApiBaseUrl/api/v1.1/curriculum/$cienciaID/"
    }

    private fun toDTO(user: User): CvDTO {
        return CvDTO(
            summary = user.summary,
            identifiers = user.identifiers.map { IdentifierDTO(it.id, it.type) },
            languages = languagesToDTO(user.languages),
            distinctions = distinctionsToDTO(user.distinctions),
            addresses = addressesToDTO(user.addresses),
            emails = user.emails.map { EmailDTO(it.id, it.type, it.address) },
            phones = phonesToDTO(user.phones),
            websites = user.websites.map { WebsiteDTO(it.id, it.type, it.url) },
            educations = educationsToDTO(user.educations),
            projects = projectsToDTO(user.projects),
            productions = productionsToDTO(user.productions),
            profExp = profExpToDTO(user.profExp),
            activities = activitiesToDTO(user.activities),
            authors = user.authors.map { AuthorDTO(it.id, it.citationName) }
        )
    }
    private fun languagesToDTO(languages: List<Language>): List<LanguageDTO> {
        return languages.map {
            LanguageDTO(it.id,
                it.language,
                it.comprehension,
                it.reading,
                it.speaking,
                it.writing)
        }
    }
    private fun distinctionsToDTO(distinctions: List<Distinction>): List<DistinctionDTO> {
        return distinctions.map {
            DistinctionDTO(
                id = it.id,
                type = it.type,
                name = it.name,
                year = it.year,
                promotingEntity = it.promotingEntity
            )
        }
    }
    private fun addressesToDTO(addresses: List<Address>): List<AddressDTO> {
        return addresses.map {
            AddressDTO(
                id = it.id,
                type = it.type,
                address = it.address,
                zipCode = it.zipCode,
                locality = it.locality,
                municipality = it.municipality,
                country = it.country
            )
        }
    }
    private fun phonesToDTO(phones: List<Phone>): List<PhoneDTO> {
        return phones.map {
            PhoneDTO(
                id = it.id,
                type = it.type,
                number = it.number,
                countryCode = it.countryCode,
                device = it.device
            )
        }
    }
    private fun educationsToDTO(educations: List<Education>): List<EducationDTO> {
        return educations.map {
            val thesis = it.thesis?.let { thesis ->
                ThesisDTO(
                    id = thesis.id,
                    title = thesis.title,
                    date = thesis.date,
                    supervisors = thesis.supervisors.map { supervisor ->
                        SupervisorDTO(supervisor.id.name, supervisor.role) }
                )
            }
            EducationDTO(
                id = it.id,
                degree = it.degree,
                course = it.course,
                institution = it.institution,
                classification = it.classification,
                status = it.status,
                courseCode = it.courseCode,
                startDate = it.startDate,
                endDate = it.endDate,
                thesis = thesis
            )
        }
    }
    private fun projectsToDTO(projects: List<Project>): List<ProjectDTO> {
        return projects.map {
            ProjectDTO(
                id = it.id,
                title = it.title,
                institution = it.institution,
                startDate = it.startDate,
                endDate = it.endDate,
                description = it.description,
                fundingType = it.fundingType,
                identifier = it.identifier,
                role = it.role,
                state = it.state,
                authors = exportService.getProjectAuthors(it).map { author -> author.citationName }
            )
        }
    }
    private fun productionsToDTO(productions: List<Production>): List<ProductionDTO> {
        val prod: MutableList<ProductionDTO> = mutableListOf()
        productions.forEach {
            val authors = exportService.getProductionAuthors(it).map { author -> author.citationName }
            val productionDTO = ProductionMapper.toDTO(it, authors)
            if (productionDTO != null) {
                prod.add(productionDTO)
            }
        }
        return prod
    }
    private fun profExpToDTO(profExp: List<ProfessionalExperience>): List<ProfessionalExperienceDTO> {
        return profExp.map {
            ProfessionalExperienceDTO(
                id = it.id,
                institution = it.institution,
                role = it.role,
                description = it.description,
                startDate = it.startDate,
                endDate = it.endDate
            )
        }
    }
    private fun activitiesToDTO(activities: List<Activity>): List<ActivityDTO> {
        val activity: MutableList<ActivityDTO> = mutableListOf()
        activities.forEach {
            val activityDTO = ActivityMapper.toDTO(it)
            if (activityDTO != null) {
                activity.add(activityDTO)
            }
        }
        return activity
    }
}