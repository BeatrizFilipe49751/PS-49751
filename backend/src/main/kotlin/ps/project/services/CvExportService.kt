package ps.project.services

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.reactive.function.client.WebClient
import ps.project.domain.User
import ps.project.domain.activity.AcademicJury
import ps.project.domain.activity.Activity
import ps.project.domain.activity.ActivityType
import ps.project.domain.activity.Consulting
import ps.project.domain.activity.DegreeType
import ps.project.domain.activity.Event
import ps.project.domain.activity.SubjectTaught
import ps.project.domain.activity.Supervision
import ps.project.domain.author.Author
import ps.project.domain.contact.Address
import ps.project.domain.contact.ContactType
import ps.project.domain.contact.Email
import ps.project.domain.contact.Phone
import ps.project.domain.contact.PhoneDevice
import ps.project.domain.contact.Website
import ps.project.domain.contact.WebsiteType
import ps.project.domain.distinction.Distinction
import ps.project.domain.distinction.DistinctionType
import ps.project.domain.education.Education
import ps.project.domain.education.EducationDegree
import ps.project.domain.education.EducationStatus
import ps.project.domain.education.SupervisorRole
import ps.project.domain.production.Thesis
import ps.project.domain.identifier.Identifier
import ps.project.domain.identifier.IdentifierType
import ps.project.domain.language.Language
import ps.project.domain.language.LanguageLevel
import ps.project.domain.production.Book
import ps.project.domain.production.BookChapter
import ps.project.domain.production.EditedBook
import ps.project.domain.production.Journal
import ps.project.domain.production.Magazine
import ps.project.domain.production.Newspaper
import ps.project.domain.production.Other
import ps.project.domain.production.Patent
import ps.project.domain.production.Production
import ps.project.domain.production.ProductionType
import ps.project.domain.production.Report
import ps.project.domain.production.ResearchTechnique
import ps.project.domain.production.Software
import ps.project.domain.production.Translation
import ps.project.domain.production.WebsiteProd
import ps.project.domain.profExp.Others
import ps.project.domain.profExp.Positions
import ps.project.domain.profExp.ProfExpType
import ps.project.domain.profExp.ProfessionalExperience
import ps.project.domain.profExp.Science
import ps.project.domain.profExp.TeachingHE
import ps.project.domain.project.FundingType
import ps.project.domain.project.Project
import ps.project.domain.project.ProjectState
import ps.project.mapping.xml.cienciaVitae.*
import ps.project.mapping.xml.cienciaVitae.activity.AcademicJuryXml
import ps.project.mapping.xml.cienciaVitae.activity.ConsultingAdvisoryXml
import ps.project.mapping.xml.cienciaVitae.activity.CourseTaughtXml
import ps.project.mapping.xml.cienciaVitae.activity.EventAdministrationXml
import ps.project.mapping.xml.cienciaVitae.activity.ServiceXml
import ps.project.mapping.xml.cienciaVitae.activity.StudentXml
import ps.project.mapping.xml.cienciaVitae.activity.SupervisionXml
import ps.project.repository.ProductionAuthorRepository
import ps.project.repository.ProjectAuthorRepository
import ps.project.mapping.xml.cienciaVitae.common.CodeValueXml
import ps.project.mapping.xml.cienciaVitae.common.DateXml
import ps.project.mapping.xml.cienciaVitae.common.InstitutionXml
import ps.project.mapping.xml.cienciaVitae.common.InstitutionsXml
import ps.project.mapping.xml.cienciaVitae.contacts.AddressXml
import ps.project.mapping.xml.cienciaVitae.contacts.EmailXml
import ps.project.mapping.xml.cienciaVitae.contacts.PhoneXml
import ps.project.mapping.xml.cienciaVitae.production.*
import ps.project.mapping.xml.cienciaVitae.contacts.WebsiteXml
import ps.project.mapping.xml.cienciaVitae.education.EducationXml
import ps.project.mapping.xml.cienciaVitae.education.SupervisorXml
import ps.project.mapping.xml.cienciaVitae.education.ThesisXml
import ps.project.mapping.xml.cienciaVitae.profExp.EmploymentXml
import ps.project.mapping.xml.cienciaVitae.project.*
import java.time.LocalDate

@Service
class CvExportService(
    private val webClient: WebClient,
    private val projectAuthorRepository: ProjectAuthorRepository,
    private val productionAuthorRepository: ProductionAuthorRepository,
) {
    private val xmlMapper = XmlMapper()
        .registerKotlinModule()
        .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)

    fun sendCvToCienciaVitae(user: User, baseUrl: String) {
        sendPhonesToCienciaVitae(user.phones, baseUrl)
        sendEmailsToCienciaVitae(user.emails, baseUrl)
        if (!user.summary.isNullOrBlank()) {
            sendSummaryToCienciaVitae(user.summary, baseUrl)
        }
        sendWebsitesToCienciaVitae(user.websites, baseUrl)
        sendAddressesToCienciaVitae(user.addresses, baseUrl)
        sendLanguagesToCienciaVitae(user.languages, baseUrl)
        sendIdentifiersToCienciaVitae(user.identifiers, baseUrl)
        sendDistinctionsToCienciaVitae(user.distinctions, baseUrl)
        sendAuthorsToCienciaVitae(user.authors, baseUrl)
        sendEducationsToCienciaVitae(user.educations, baseUrl)
        sendActivitiesToCienciaVitae(user.activities, baseUrl)
        sendProfExperiencesToCienciaVitae(user.profExp, baseUrl)
        sendProjectsToCienciaVitae(user.projects, baseUrl)
        sendProductionsToCienciaVitae(user.productions, baseUrl)
    }

    private fun sendPhonesToCienciaVitae(phones: List<Phone>, baseUrl: String) {
        phones.forEach {
            val device = PhoneDevice.valueOf(it.type)
            val type = ContactType.valueOf(it.type)
            val payload = PhoneXml(
                usageType = CodeValueXml(type.code, type.name),
                phoneType = CodeValueXml(device.code.toString(), device.value),
                countryCode = it.countryCode,
                number = it.number
            )
            postToCienciaVitae(baseUrl, "phone-number", payload, "phone number")
        }
    }

    private fun sendEmailsToCienciaVitae(emails: List<Email>, baseUrl: String) {
        emails.forEach {
            val type = ContactType.valueOf(it.type)
            val payload = EmailXml(
                emailType = CodeValueXml(type.code, type.name),
                email = it.address
            )
            postToCienciaVitae(baseUrl, "email", payload, "email")
        }
    }

    private fun sendSummaryToCienciaVitae(summary: String?, baseUrl: String) {
        if (summary == null) {
            return
        } else {
            val payload = SummaryXml(
                value = summary
            )
            postToCienciaVitae(baseUrl, "resume", payload, "summary")
        }
    }

    private fun sendAuthorsToCienciaVitae(authors: List<Author>, baseUrl: String) {
        authors.forEach {
            val payload = AuthorXml(
                value = it.citationName
            )
            postToCienciaVitae(baseUrl,"citation-name", payload, "author")
        }
    }

    private fun sendWebsitesToCienciaVitae(websites: List<Website>, baseUrl: String) {
        websites.forEach {
            val type = WebsiteType.valueOf(it.type)
            val payload = WebsiteXml(
                siteType = CodeValueXml(type.code.toString(), type.value),
                url = it.url
            )
            postToCienciaVitae(baseUrl, "web-address", payload, "website")
        }
    }

    private fun sendAddressesToCienciaVitae(addresses: List<Address>, baseUrl: String) {
        addresses.forEach {
            val type = ContactType.valueOf(it.type)
            val payload = AddressXml(
                addressType = CodeValueXml(type.code, type.name),
                streetAddress = it.address,
                postalCode = it.zipCode,
                city = it.locality,
                provinceState = it.municipality,
                country = CodeValueXml(null, it.country)
            )
            postToCienciaVitae(baseUrl, "mailing-address", payload, "address")
        }
    }

    private fun sendLanguagesToCienciaVitae(languages: List<Language>, baseUrl: String) {
        languages.forEach {
            val payload = LanguageXml(
                language = CodeValueXml(null, it.language),
                read = buildLanguageXml(it.reading),
                write = buildLanguageXml(it.writing),
                speak = buildLanguageXml(it.speaking),
                understandSpoken = buildLanguageXml(it.comprehension)
            )
            postToCienciaVitae(baseUrl, "language-competency", payload, "language")
        }
    }

    private fun buildLanguageXml(levelString: String?): CodeValueXml? {
        return if (levelString != null) {
            val level = LanguageLevel.valueOf(levelString)
            CodeValueXml(level.code, level.value)
        } else {
            null
        }
    }

    private fun sendIdentifiersToCienciaVitae(identifiers: List<Identifier>, baseUrl: String) {
        identifiers.forEach {
            val type = IdentifierType.valueOf(it.type)
            val payload = IdentifierXml(
                identifierType = CodeValueXml(type.name, type.value),
                identifier = it.id
            )
            postToCienciaVitae(baseUrl, "author-identifier", payload, "identifier")
        }
    }

    private fun sendDistinctionsToCienciaVitae(distinctions: List<Distinction>, baseUrl: String) {
        distinctions.forEach {
            val institutions = it.promotingEntity?.let { name ->
                InstitutionsXml(institution = listOf(InstitutionXml(name = name)))
            }
            val type = DistinctionType.valueOf(it.type)
            val payload = DistinctionXml(
                distinctionType = CodeValueXml(code = type.code, value = type.value),
                distinctionName = it.name,
                effectiveDate = it.year,
                institutions = institutions
            )
            postToCienciaVitae(baseUrl, "distinction", payload, "distinction")
        }
    }

    private fun sendEducationsToCienciaVitae(educations: List<Education>, baseUrl: String) {
        educations.forEach {
            val degree = EducationDegree.valueOf(it.degree)
            val status = EducationStatus.valueOf(it.status)
            val payload = EducationXml(
                degreeType = CodeValueXml(degree.code, degree.value),
                degreeCode = CodeValueXml(null, it.courseCode),
                degreeName = it.course,
                institutions = buildInstitutionXml(it.institution)!!,
                classification = it.classification,
                degreeStatus = CodeValueXml(status.code.toString(), status.name),
                startDate = buildDateXml(it.startDate),
                endDate = buildDateXml(it.endDate) ?: DateXml(1,1,1),
                thesis = buildThesisXml(it.thesis)
            )
            postToCienciaVitae(baseUrl, "degree", payload, "education")
        }
    }

    private fun sendActivitiesToCienciaVitae(activities: List<Activity>, baseUrl: String) {
        activities.forEach {
            val payload = buildDetailedActivityBlock(it)
            if (payload is ServiceXml) {
                postToCienciaVitae(baseUrl, "output", payload, "activity")
            }
        }
    }

    private fun buildDetailedActivityBlock(activity: Activity): ServiceXml? {
        return when (activity) {
            is Consulting -> {
                val type = ActivityType.Consulting
                ServiceXml(
                    serviceCategory = CodeValueXml(type.code, type.value),
                    consultingAdvisory = ConsultingAdvisoryXml (
                        title = activity.title,
                        startDate = buildDateXml(activity.date)!!,
                        endDate = buildDateXml(activity.endDate),
                    )
                )
            }
            is AcademicJury -> {
                val type = ActivityType.AcademicJury
                val degree = DegreeType.valueOf(activity.degree)
                ServiceXml(
                    serviceCategory = CodeValueXml(type.code, type.value),
                    academicJury = AcademicJuryXml(
                        title = activity.title,
                        date = buildDateXml(activity.date)!!,
                        student = activity.candidate,
                        degree = CodeValueXml(degree.code, degree.value),
                        course = activity.course,
                        institutions = buildInstitutionXml(activity.institution)
                    )
                )
            }
            is Event -> {
                val type = ActivityType.Event
                ServiceXml(
                    serviceCategory = CodeValueXml(type.code, type.value),
                    eventAdministration = EventAdministrationXml(
                        title = activity.title,
                        startDate = buildDateXml(activity.date)!!,
                        institutions = buildInstitutionXml(activity.institution)
                    )
                )
            }
            is SubjectTaught -> {
                val type = ActivityType.SubjectTaught
                ServiceXml(
                    serviceCategory = CodeValueXml(type.code, type.value),
                    courseTaught = CourseTaughtXml(
                        title = activity.title,
                        startDate = buildDateXml(activity.date)!!,
                        endDate = buildDateXml(activity.endDate),
                        course = activity.course,
                        courseCode = activity.courseCode
                    )
                )
            }
            is Supervision -> {
                val type = ActivityType.Supervision
                val role = SupervisorRole.valueOf(activity.role)
                val degree = DegreeType.valueOf(activity.degree)
                ServiceXml(
                    serviceCategory = CodeValueXml(type.code, type.value),
                    supervision = SupervisionXml(
                        title = activity.title,
                        startDate = buildDateXml(activity.date)!!,
                        student = StudentXml(name = activity.supervisee, consent = false),
                        course = activity.course,
                        courseCode = activity.courseCode,
                        supervisoryType = CodeValueXml(role.code, role.value),
                        degree = CodeValueXml(degree.code, degree.value),
                        institutions = buildInstitutionXml(activity.institution)
                    )
                )
            }
            else -> null
        }
    }

    private fun sendProfExperiencesToCienciaVitae(profExp: List<ProfessionalExperience>, baseUrl: String) {
        profExp.forEach {
            val type = when (it) {
                is Science -> ProfExpType.Science
                is TeachingHE -> ProfExpType.TeachingHE
                is Positions -> ProfExpType.Positions
                is Others -> ProfExpType.Others
                else -> ProfExpType.Others
            }
            val payload = EmploymentXml(
                employmentCategory = CodeValueXml(type.code, type.value),
                institution = InstitutionXml(name = it.institution),
                startDate = buildDateXml(it.startDate)!!,
                endDate = buildDateXml(it.endDate)
            )
            postToCienciaVitae(baseUrl, "employment", payload, "professional experience")
        }
    }

    private fun buildThesisXml(thesis: Thesis?): ThesisXml? {
        return thesis?.let {
            val supervisorsXml = if (it.supervisors.isNotEmpty()) {
                val supervisors = mutableListOf<SupervisorXml>()
                it.supervisors.forEach{supervisor ->
                    val role = SupervisorRole.valueOf(supervisor.role)
                    supervisors.add(
                        SupervisorXml(
                            supervisorName = supervisor.id.name,
                            supervisorRole = CodeValueXml(role.code, role.value)
                        )
                    )
                }
                supervisors
            } else {
                null
            }
            return ThesisXml(
                thesisTitle = it.title,
                supervisors = supervisorsXml
            )
        }
    }

    private fun sendProjectsToCienciaVitae(projects: List<Project>, baseUrl: String) {
        projects.forEach {
            val authors = getProjectAuthors(it)
            val fundingIdentifiers = it.identifier?.let { id ->
                FundingIdentifiersXml(
                    total = 1,
                    identifiers = listOf(FundingIdentifierXml(reference = id))
                )
            }
            val fundingType = FundingType.valueOf(it.fundingType)
            val state = it.state?.let { state -> ProjectState.valueOf(state) }
            val payload = ProjectXml(
                fundingCategory = CodeValueXml(fundingType.code, fundingType.name),
                title = it.title,
                description = it.description,
                startDate = buildDateXml(it.startDate)!!,
                endDate = buildDateXml(it.endDate),
                status = CodeValueXml(state?.code, state?.name),
                role = CodeValueXml(null, it.role),
                institution = buildInstitutionXml(it.institution),
                fundingIdentifiers = fundingIdentifiers,
                investigators = InvestigatorsXml(
                    total = authors.size,
                    investigators = authors.map { author -> InvestigatorXml(author.citationName) }
                )
            )
            postToCienciaVitae(baseUrl, "funding", payload, "project")
        }
    }

    private fun sendProductionsToCienciaVitae(productions: List<Production>, baseUrl: String) {
        productions.forEach {
            val payload = buildDetailedOutputBlock(it)
            if (payload is OutputXml) {
                postToCienciaVitae(baseUrl, "output", payload, "production")
            }
        }
    }

    private fun buildDetailedOutputBlock(it: Production): OutputXml? {
        val authors = getProductionAuthors(it)
        val authorsXml = buildAuthorsXml(authors)

        return when (it) {
            is Journal -> {
                val type = ProductionType.JOURNAL_ARTICLE
                val identifiersXml = buildIdentifiersXml(it.doi, null, it.issn)
                OutputXml(
                    outputCategory = buildProdCategory(type),
                    outputType = buildProdType(type),
                    journalArticle = JournalArticleXml(it.title, it.secondaryTitle, it.volume, it.pageFrom, it.pageTo,
                        buildDateXml(it.date)!!, it.url, identifiersXml, authorsXml)
                )
            }
            is Book -> {
                val type = ProductionType.BOOK
                val identifiersXml = buildIdentifiersXml(it.doi, it.isbn, null)
                OutputXml(
                    outputCategory = buildProdCategory(type),
                    outputType = buildProdType(type),
                    book = BookXml(it.title, it.volume, it.date.year, it.url, identifiersXml, authorsXml)
                )
            }
            is EditedBook -> {
                val type = ProductionType.EDITED_BOOK
                val identifiersXml = buildIdentifiersXml(it.doi, it.isbn, null)
                OutputXml(
                    outputCategory = buildProdCategory(type),
                    outputType = buildProdType(type),
                    editedBook = EditedBookXml(it.title, it.volume, it.date.year, it.url, identifiersXml, authorsXml)
                )
            }
            is BookChapter -> {
                val type = ProductionType.BOOK_CHAPTER
                val identifiersXml = buildIdentifiersXml(it.doi, it.isbn, null)
                OutputXml(
                    outputCategory = buildProdCategory(type),
                    outputType = buildProdType(type),
                    bookChapter = BookChapterXml(it.title, it.secondaryTitle, it.volume, it.pageFrom, it.pageTo,
                        it.date.year, it.url, identifiersXml, authorsXml)
                )
            }
            is Translation -> {
                val type = ProductionType.TRANSLATION
                val identifiersXml = buildIdentifiersXml(it.doi, null, null)
                OutputXml(
                    outputCategory = buildProdCategory(type),
                    outputType = buildProdType(type),
                    translation = TranslationXml(it.title, it.secondaryTitle, it.volume,
                        it.date.year, it.url, identifiersXml, authorsXml)
                )
            }
            is Newspaper -> {
                val type = ProductionType.NEWSPAPER_ARTICLE
                val identifiersXml = buildIdentifiersXml(it.doi, null, it.issn)
                OutputXml(
                    outputCategory = buildProdCategory(type),
                    outputType = buildProdType(type),
                    newspaperArticle = NewspaperArticleXml(it.title, it.secondaryTitle, it.volume, it.pageFrom, it.pageTo,
                        buildDateXml(it.date)!!, it.url, identifiersXml, authorsXml)
                )
            }
            is Magazine -> {
                val type = ProductionType.MAGAZINE_ARTICLE
                val identifiersXml = buildIdentifiersXml(it.doi, null, it.issn)
                OutputXml(
                    outputCategory = buildProdCategory(type),
                    outputType = buildProdType(type),
                    magazineArticle = MagazineArticleXml(
                        it.title, it.secondaryTitle, it.volume, it.pageFrom, it.pageTo,
                        buildDateXml(it.date)!!, it.url, identifiersXml, authorsXml
                    )
                )
            }
            is Report -> {
                val type = ProductionType.REPORT
                val identifiersXml = buildIdentifiersXml(it.doi, null, null)
                OutputXml(
                    outputCategory = buildProdCategory(type),
                    outputType = buildProdType(type),
                    report = ReportXml(it.title, it.volume, buildDateXml(it.date)!!, it.url, identifiersXml, authorsXml)
                )
            }
            is WebsiteProd -> {
                val type = ProductionType.WEBSITE
                OutputXml(
                    outputCategory = buildProdCategory(type),
                    outputType = buildProdType(type),
                    website = WebsiteXml(it.title, it.description, buildDateXml(it.date)!!,
                        it.url, null, authorsXml)
                )
            }
            is Patent -> {
                val type = ProductionType.PATENT
                OutputXml(
                    outputCategory = buildProdCategory(type),
                    outputType = buildProdType(type),
                    patent = PatentXml(it.title, buildDateXml(it.date)!!, null, authorsXml)
                )
            }
            is ResearchTechnique -> {
                val type = ProductionType.RESEARCH_TECHNIQUE
                val identifiersXml = buildIdentifiersXml(it.doi, null, null)
                OutputXml(
                    outputCategory = buildProdCategory(type),
                    outputType = buildProdType(type),
                    researchTechnique = ResearchTechniqueXml(it.title, buildDateXml(it.date)!!, identifiersXml, authorsXml)
                )
            }
            is Software -> {
                val type = ProductionType.SOFTWARE
                val identifiersXml = buildIdentifiersXml(it.doi, null, null)
                OutputXml(
                    outputCategory = buildProdCategory(type),
                    outputType = buildProdType(type),
                    software = SoftwareXml(it.title, it.description, it.version!!, it.platform!!,
                        buildDateXml(it.date)!!, identifiersXml, authorsXml)
                )
            }
            is Other -> {
                val type = ProductionType.OTHER_OUTPUT
                OutputXml(
                    outputCategory = buildProdCategory(type),
                    outputType = buildProdType(type),
                    otherOutput = OtherOutputXml(it.title, it.description, buildDateXml(it.date)!!,
                        it.url, null, authorsXml)
                )
            }
            else -> {return null}
        }
    }

    private fun buildProdCategory(type: ProductionType): CodeValueXml {
        return CodeValueXml(type.categoryCode, type.categoryName)
    }

    private fun buildProdType(type: ProductionType): CodeValueXml {
        return CodeValueXml(type.code, type.value)
    }

    private fun buildIdentifiersXml(doi: String?, isbn: String?, issn: String?) : List<OutputIdentifierXml>? {
        return listOfNotNull(
            doi?.let {
                OutputIdentifierXml(it, CodeValueXml("doi", "doi: Digital object identifier"))
            },
            isbn?.let {
                OutputIdentifierXml(it, CodeValueXml("isbn", "isbn: International Standard Book Number"))
            },
            issn?.let {
                OutputIdentifierXml(it, CodeValueXml("issn", "issn: International Standard Serial Number"))
            }
        ).takeIf { it.isNotEmpty() }
    }

    private fun buildAuthorsXml(authors: List<Author>): OutputAuthorsWrapper? {
        if (authors.isEmpty()) return null
        val xmlAuthors = authors.map { OutputAuthorXml(it.citationName) }
        val citation = authors.joinToString("; ") { it.citationName }
        return OutputAuthorsWrapper(
            total = xmlAuthors.size,
            authors = xmlAuthors,
            citation = citation
        )
    }

    private fun buildDateXml(date: LocalDate?): DateXml? {
        return date?.let {
            DateXml(
                year = it.year,
                month = it.monthValue,
                day = it.dayOfMonth
            )
        }
    }

    private fun buildInstitutionXml(institution: String?) : InstitutionsXml? {
        return if (institution == null) {
            null
        } else {
            InstitutionsXml(institution = listOf(InstitutionXml(name = institution)))
        }
    }

    fun getProjectAuthors(project: Project): List<Author> {
        return projectAuthorRepository.findByProject(project).map { projectAuthor ->  projectAuthor.author }
    }
    fun getProductionAuthors(production: Production): List<Author> {
        return productionAuthorRepository.findByProduction(production).map { productionAuthor ->  productionAuthor.author }
    }

    private fun postToCienciaVitae(
        baseUrl: String,
        endpoint: String,
        payload: Any,
        context: String
    ) {
        val xml = xmlMapper.writeValueAsString(payload)
        val fullXml = "<?xml version=\"1.0\"?>\n$xml"
        val url = "$baseUrl$endpoint"
        val response = postRequest(url, fullXml)
        checkResponse(response, context)
    }


    private fun postRequest(url: String, xml: String): ResponseEntity<Void> {
        return webClient.post()
            .uri(url)
            .contentType(MediaType.APPLICATION_XML)
            .bodyValue(xml)
            .retrieve()
            .toBodilessEntity()
            .block() ?: throw Exception("Error retrieving response from CienciaVITAE")
    }

    private fun checkResponse(response: ResponseEntity<Void>, context: String) {
        val statusCode = response.statusCode
        when {
            statusCode.is4xxClientError -> throw HttpClientErrorException(statusCode, "Client error while posting $context")
            statusCode.is5xxServerError -> throw HttpServerErrorException(statusCode, "Server error while posting $context")
        }
    }
}