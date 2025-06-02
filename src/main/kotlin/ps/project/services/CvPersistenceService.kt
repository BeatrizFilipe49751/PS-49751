package ps.project.services

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Service
import ps.project.domain.Cv
import ps.project.domain.CvDTO
import ps.project.domain.associations.ProductionAuthor
import ps.project.domain.associations.ProjectAuthor
import ps.project.domain.author.Author
import ps.project.domain.education.Education
import ps.project.domain.education.EducationDTO
import ps.project.domain.education.Supervisor
import ps.project.domain.education.Thesis
import ps.project.domain.production.ProductionDTO
import ps.project.domain.project.ProjectDTO
import ps.project.repository.*

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

    fun saveCvFromDto(cv: CvDTO, savedCv: Cv) {
        saveEntities(cv.identifiers, { it.toEntity(savedCv) }, identifierRepository::saveAll)
        saveEntities(cv.languages, { it.toEntity(savedCv) }, languageRepository::saveAll)
        saveEntities(cv.distinctions, { it.toEntity(savedCv) }, distinctionRepository::saveAll)
        importEducations(cv.educations, savedCv)
        saveEntities(cv.addresses, { it.toEntity(savedCv) }, addressRepository::saveAll)
        saveEntities(cv.emails, { it.toEntity(savedCv) }, emailRepository::saveAll)
        saveEntities(cv.phones, { it.toEntity(savedCv) }, phoneRepository::saveAll)
        saveEntities(cv.websites, { it.toEntity(savedCv) }, websiteRepository::saveAll)
        saveEntities(cv.profExp, { it.toEntity(savedCv) }, profExpRepository::saveAll)
        saveEntities(cv.activities, { it.toEntity(savedCv) }, activityRepository::saveAll)
        saveEntities(cv.authors, { it.toEntity(savedCv) }, authorRepository::saveAll)
        clearManager()
        importProjects(cv.projects, savedCv)
        clearManager()
        importProductions(cv.productions, savedCv)
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

    private fun importEducations(educations: List<EducationDTO>, savedCv: Cv) {
        val educationEntities = mutableListOf<Education>()
        val thesisEntities = mutableListOf<Thesis>()
        val supervisorEntities = mutableListOf<Supervisor>()

        for (eduDTO in educations) {
            val edu = eduDTO.toEntity(savedCv)
            educationEntities.add(edu)

            eduDTO.thesis?.let { thesisDTO ->
                val thesis = thesisDTO.toEntity(edu)
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

    private fun importProjects(projects: List<ProjectDTO>, savedCv: Cv) {
        projects.forEach { project ->
            val proj = project.toEntity(savedCv)
            projectRepository.save(proj)
            project.authors.forEach { name ->
                val author = authorRepository.findByCitationName(name) ?:
                authorRepository.save(Author(citationName = name))
                val projectAuthor = ProjectAuthor(
                    project = proj,
                    author = author
                )
                projectAuthorRepository.save(projectAuthor)
            }
        }
    }

    private fun importProductions(productions: List<ProductionDTO>, savedCv: Cv) {
        productions.forEach { production ->
            val prod = production.toEntity(savedCv)
            productionRepository.save(prod)
            production.authors.forEach { name ->
                val author = authorRepository.findByCitationName(name) ?:
                authorRepository.save(Author(citationName = name))
                val productionAuthor = ProductionAuthor(
                    production = prod,
                    author = author
                )
                productionAuthorRepository.save(productionAuthor)
            }
        }
    }
}
