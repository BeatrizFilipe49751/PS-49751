package ps.project.services

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Service
import ps.project.domain.CvDTO
import ps.project.domain.User
import ps.project.domain.activity.ActivityDTO
import ps.project.domain.activity.ActivityMapper
import ps.project.domain.associations.ProductionAuthor
import ps.project.domain.associations.ProjectAuthor
import ps.project.domain.author.Author
import ps.project.domain.education.Education
import ps.project.domain.education.EducationDTO
import ps.project.domain.education.Supervisor
import ps.project.domain.production.Thesis
import ps.project.domain.production.ProductionDTO
import ps.project.domain.production.ProductionMapper
import ps.project.domain.project.ProjectDTO
import ps.project.repository.*
import ps.project.repository.ProductionRepository

@Service
class CvPersistenceService(
    private val identifierRepository: IdentifierRepository,
    private val languageRepository: LanguageRepository,
    private val distinctionRepository: DistinctionRepository,
    private val educationRepository: EducationRepository,
    private val addressRepository: AddressRepository,
    private val emailRepository: EmailRepository,
    private val phoneRepository: PhoneRepository,
    private val websiteRepository: WebsiteRepository,
    private val thesisRepository: ThesisRepository,
    private val supervisorRepository: SupervisorRepository,
    private val projectRepository: ProjectRepository,
    private val productionRepository: ProductionRepository,
    private val profExpRepository: ProfExpRepository,
    private val activityRepository: ActivityRepository,
    private val authorRepository: AuthorRepository,
    private val projectAuthorRepository: ProjectAuthorRepository,
    private val productionAuthorRepository: ProductionAuthorRepository,
) {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    fun saveCvFromDto(cv: CvDTO, user: User) {
        saveEntities(cv.identifiers, { it.toEntity(user) }, identifierRepository::saveAll)
        saveEntities(cv.languages, { it.toEntity(user) }, languageRepository::saveAll)
        saveEntities(cv.distinctions, { it.toEntity(user) }, distinctionRepository::saveAll)
        importEducations(cv.educations, user)
        saveEntities(cv.addresses, { it.toEntity(user) }, addressRepository::saveAll)
        saveEntities(cv.emails, { it.toEntity(user) }, emailRepository::saveAll)
        saveEntities(cv.phones, { it.toEntity(user) }, phoneRepository::saveAll)
        saveEntities(cv.websites, { it.toEntity(user) }, websiteRepository::saveAll)
        saveEntities(cv.profExp, { it.toEntity(user) }, profExpRepository::saveAll)
        importActivities(cv.activities, user)
        saveEntities(cv.authors, { it.toEntity(user) }, authorRepository::saveAll)
        clearManager()
        importProjects(cv.projects, user)
        clearManager()
        importProductions(cv.productions, user)
        clearManager()
    }

    private fun clearManager() {
        entityManager.flush()
        entityManager.clear()
    }

    private fun <D, E> saveEntities(
        dtos: List<D>,
        toEntity: (D) -> E,
        saveAll: (List<E>) -> Unit
    ) {
        val entities = dtos.map(toEntity)
        saveAll(entities)
    }

    private fun importEducations(educations: List<EducationDTO>, user: User) {
        val educationEntities = mutableListOf<Education>()
        val thesisEntities = mutableListOf<Thesis>()
        val supervisorEntities = mutableListOf<Supervisor>()

        for (eduDTO in educations) {
            val edu = eduDTO.toEntity(user)
            educationEntities.add(edu)

            eduDTO.thesis?.let { thesisDTO ->
                val thesis = thesisDTO.toEntity(user, edu)
                thesisEntities.add(thesis)

                thesisDTO.supervisors.forEach { supervisorDTO ->
                    val supervisor = supervisorDTO.toEntity(thesis)
                    supervisorEntities.add(supervisor)
                }
            }
        }
        educationRepository.saveAll(educationEntities)
        thesisRepository.saveAll(thesisEntities)
        supervisorRepository.saveAll(supervisorEntities)
    }

    private fun importActivities(activities: List<ActivityDTO>, user: User) {
        activities.forEach { activity ->
            val activityEntity = ActivityMapper.toEntity(activity, user)
            if (activityEntity != null) {
                activityRepository.save(activityEntity)
            }
        }
    }

    private fun importProjects(projects: List<ProjectDTO>, user: User) {
        projects.forEach { project ->
            val proj = project.toEntity(user)
            projectRepository.save(proj)
            project.authors.forEach { name ->
                val author = authorRepository.findByCitationName(name) ?:
                authorRepository.save(Author(user = user, citationName = name))
                val projectAuthor = ProjectAuthor(
                    project = proj,
                    author = author
                )
                projectAuthorRepository.save(projectAuthor)
            }
        }
    }

    private fun importProductions(productions: List<ProductionDTO>, user: User) {
        productions.forEach { production ->
            val prod = ProductionMapper.toEntity(production, user)
            if (prod != null) {
                productionRepository.save(prod)
                production.authors.forEach { name ->
                    val author = authorRepository.findByCitationName(name) ?:
                    authorRepository.save(Author(user = user, citationName = name))
                    val productionAuthor = ProductionAuthor(
                        production = prod,
                        author = author
                    )
                    productionAuthorRepository.save(productionAuthor)
                }
            }
        }
    }
}
