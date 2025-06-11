package ps.project.mapping.mappers.lattes

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.stereotype.Service
import ps.project.domain.*
import ps.project.domain.activity.ActivityDTO
import ps.project.domain.author.AuthorDTO
import ps.project.domain.distinction.DistinctionDTO
import ps.project.domain.education.*
import ps.project.domain.identifier.IdentifierDTO
import ps.project.domain.identifier.IdentifierType
import ps.project.domain.language.LanguageDTO
import ps.project.domain.profExp.ProfessionalExperienceDTO
import ps.project.domain.project.FundingType
import ps.project.domain.project.ProjectDTO
import ps.project.mapping.xml.lattes.*
import ps.project.mapping.xml.lattes.education.LattesEducation
import java.time.LocalDate

@Service
class LattesMapper(
    private val productionMapper: LattesProductionMapper,
    private val contactsMapper: LattesContactsMapper
) : CvMapper {
    override fun supports(source: String): Boolean {
        return source.equals("lattes", ignoreCase = true)
    }

    override fun extractCv(xmlString: String): CvDTO {
        val mapper = XmlMapper()
            .registerKotlinModule()
            .registerModule(EmptyStringAsNullModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        val cv = mapper.readValue(xmlString, LattesCVModel::class.java)
        val data = cv.generalData
        val contacts = contactsMapper.extractContacts(data)

        return CvDTO(
            summary = extractSummary(data),
            identifiers = extractIdentifiers(cv),
            languages = extractLanguages(data),
            distinctions = extractDistinctions(data),
            educations = extractEducations(data),
            addresses = contacts.addresses,
            emails = contacts.emails,
            phones = contacts.phones,
            websites = contacts.websites,
            projects = extractProjects(data),
            productions = productionMapper.extractProductions(cv),
            profExp = extractProfExp(xmlString),
            activities = extractActivities(xmlString),
            authors = extractAuthors(data)
        )
    }

    private fun extractSummary(data: GeneralData?): String? {
        return data?.summary?.summary
    }

    private fun extractIdentifiers(cv: LattesCVModel): List<IdentifierDTO> {
        return cv.lattesIdentifier?.let { id ->
            listOf(IdentifierDTO(id = id, type = IdentifierType.LATTESID))
        } ?: emptyList()
    }

    private fun extractLanguages(data: GeneralData?): List<LanguageDTO> {
        return data?.languages?.languages?.map { it.toDTO() } ?: emptyList()
    }

    private fun extractDistinctions(data: GeneralData?): List<DistinctionDTO> {
        return data?.distinctions?.distinctions?.map { it.toDTO() } ?: emptyList()
    }

    private fun extractEducations(data: GeneralData?): List<EducationDTO> {
        val educations = mutableListOf<EducationDTO>()
        val eduWrapper = data?.educations ?: return emptyList()

        for ((entries, level) in eduWrapper.getAllEntries()) {
            for (entry in entries) {
                val institution = entry.institution
                val course = entry.course ?: continue
                val status = mapStatus(entry.status)
                val courseCode = entry.courseCode
                val startYear = entry.startYear
                val endYear = entry.endYear

                val startDate = startYear?.let { LocalDate.of(it, 1, 1) }
                val endDate = LocalDate.of(endYear, 12, 31)

                educations.add(
                    EducationDTO(
                        degree = level,
                        course = course,
                        institution = institution,
                        status = status,
                        courseCode = courseCode,
                        startDate = startDate,
                        endDate = endDate,
                        thesis = extractThesis(entry)
                    )
                )
            }
        }
        return educations
    }

    private fun extractThesis(entry: LattesEducation): ThesisDTO? {
        val thesisTitle = listOfNotNull(
            entry.thesisTitle,
            entry.finalProjectTitle
        ).firstOrNull { it.isNotBlank() }

        val supervisors = mutableListOf<SupervisorDTO>()

        entry.supervisorName?.takeIf { it.isNotBlank() }?.let {
            supervisors.add(SupervisorDTO(name = it, role = SupervisorRole.Supervisor))
        }
        entry.coSupervisorName?.takeIf { it.isNotBlank() }?.let {
            supervisors.add(SupervisorDTO(name = it, role = SupervisorRole.CoSupervisor))
        }

        return thesisTitle?.let {
            ThesisDTO(title = it, supervisors = supervisors)
        }
    }

    private fun extractProjects(data: GeneralData?): List<ProjectDTO> {
        val participations = data?.professionalActivities?.activities
            ?.flatMap { it.projectActivities?.projectParticipations ?: emptyList() }
            ?: return emptyList()

        return participations.flatMap { participation ->
            val projects = participation.researchProject ?: return@flatMap emptyList()

            projects.mapNotNull { project ->
                val title = project.title ?: return@mapNotNull null
                val authors = project.team?.members?.mapNotNull { it.citationName } ?: emptyList()

                val startYear = participation.startYear?.toIntOrNull() ?: return@mapNotNull null
                val startMonth = participation.startMonth?.toIntOrNull() ?: 1
                val startDate = LocalDate.of(startYear, startMonth, 1)

                val endDate = participation.endYear?.toIntOrNull()?.let { y ->
                    val m = participation.endMonth?.toIntOrNull() ?: 1
                    LocalDate.of(y, m, 1)
                }

                ProjectDTO(
                    title = title,
                    institution = listOfNotNull(participation.orgName, participation.unitName).joinToString(" "),
                    startDate = startDate, endDate = endDate, description = project.description,
                    identifier = project.identifier,
                    fundingType = project.funding?.funding?.mapFundingType() ?: FundingType.Other,
                    state = project.mapState(), authors = authors
                )
            }
        }
    }

    private fun extractProfExp(xmlString: String): List<ProfessionalExperienceDTO> { // ATUACOES-PROFISSIONAIS
        return emptyList()
        TODO("Not yet implemented")
    }

    private fun extractActivities(xmlString: String): List<ActivityDTO> {
        return emptyList()
        TODO("Not yet implemented")
    }

    private fun extractAuthors(data: GeneralData?): List<AuthorDTO> {
        val names = data?.authors ?: return emptyList()
        return names
            .split(";")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { AuthorDTO(citationName = it) }
    }

    private fun mapStatus(status: String?): EducationStatus {
        return when (status?.uppercase()) {
            "EM_ANDAMENTO" -> EducationStatus.Ongoing
            "CONCLUIDO" -> EducationStatus.Concluded
            "INCOMPLETO" -> EducationStatus.Attended
            else -> EducationStatus.Concluded
        }
    }
}
