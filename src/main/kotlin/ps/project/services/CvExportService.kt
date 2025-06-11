package ps.project.services

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.reactive.function.client.WebClient
import ps.project.domain.Cv
import ps.project.domain.author.Author
import ps.project.domain.contact.Address
import ps.project.domain.contact.Email
import ps.project.domain.contact.Phone
import ps.project.domain.contact.Website
import ps.project.domain.distinction.Distinction
import ps.project.domain.education.Education
import ps.project.domain.education.Thesis
import ps.project.domain.identifier.Identifier
import ps.project.domain.language.Language
import ps.project.domain.language.LanguageLevel
import ps.project.domain.production.Production
import ps.project.domain.production.ProductionType
import ps.project.domain.project.Project
import ps.project.mapping.xml.cienciaVitae.*
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
import ps.project.mapping.xml.cienciaVitae.project.*
import java.time.LocalDate

@Service
class CvExportService(
    private val webClient: WebClient,
    private val projectAuthorRepository: ProjectAuthorRepository,
    private val productionAuthorRepository: ProductionAuthorRepository,
    private val xmlMapper: XmlMapper = XmlMapper().apply {
        setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
    }
) {
    fun sendCvToCienciaVitae(cv: Cv, baseUrl: String) {
        sendPhonesToCienciaVitae(cv.phones, baseUrl)
        sendEmailsToCienciaVitae(cv.emails, baseUrl)
        if (!cv.summary.isNullOrBlank()) {
            sendSummaryToCienciaVitae(cv.summary, baseUrl)
        }
        sendWebsitesToCienciaVitae(cv.websites, baseUrl)
        sendAddressesToCienciaVitae(cv.addresses, baseUrl)
        sendLanguagesToCienciaVitae(cv.languages, baseUrl)
        sendIdentifiersToCienciaVitae(cv.identifiers, baseUrl)
        sendDistinctionsToCienciaVitae(cv.distinctions, baseUrl)
        sendAuthorsToCienciaVitae(cv.authors, baseUrl)
        sendEducationsToCienciaVitae(cv.educations, baseUrl)
        sendProjectsToCienciaVitae(cv.projects, baseUrl)
        sendProductionsToCienciaVitae(cv.productions, baseUrl)
    }

    private fun sendPhonesToCienciaVitae(phones: List<Phone>, baseUrl: String) {
        phones.forEach {
            val payload = PhoneXml(
                usageType = CodeValueXml(it.type.code, it.type.name),
                phoneType = CodeValueXml(it.device.code.toString(), it.device.value),
                countryCode = it.countryCode,
                number = it.number
            )
            postToCienciaVitae(baseUrl, "phone-number", payload, "phone number")
        }
    }

    private fun sendEmailsToCienciaVitae(emails: List<Email>, baseUrl: String) {
        emails.forEach {
            val payload = EmailXml(
                emailType = CodeValueXml(it.type.code, it.type.name),
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
            val payload = WebsiteXml(
                siteType = CodeValueXml(it.type.code.toString(), it.type.value),
                url = it.url
            )
            postToCienciaVitae(baseUrl, "web-address", payload, "website")
        }
    }

    private fun sendAddressesToCienciaVitae(addresses: List<Address>, baseUrl: String) {
        addresses.forEach {
            val payload = AddressXml(
                addressType = CodeValueXml(it.type.code, it.type.name),
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

    private fun buildLanguageXml(level: LanguageLevel?): CodeValueXml? {
        return if (level != null) {
            CodeValueXml(level.code, level.value)
        } else {
            null
        }
    }

    private fun sendIdentifiersToCienciaVitae(identifiers: List<Identifier>, baseUrl: String) {
        identifiers.forEach {
            val payload = IdentifierXml(
                identifierType = CodeValueXml(it.type.name, it.type.value),
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
            val payload = DistinctionXml(
                distinctionType = CodeValueXml(code = it.type.code, value = it.type.value),
                distinctionName = it.name,
                effectiveDate = it.year,
                institutions = institutions
            )
            postToCienciaVitae(baseUrl, "distinction", payload, "distinction")
        }
    }

    private fun sendEducationsToCienciaVitae(educations: List<Education>, baseUrl: String) {
        educations.forEach {
            val payload = EducationXml(
                degreeType = CodeValueXml(it.degree.code, it.degree.value),
                degreeCode = CodeValueXml(null, it.courseCode),
                degreeName = it.course,
                institutions = buildInstitutionXml(it.institution)!!,
                classification = it.classification,
                degreeStatus = CodeValueXml(it.status.code.toString(), it.status.name),
                startDate = buildDateXml(it.startDate),
                endDate = buildDateXml(it.endDate) ?: DateXml(1,1,1),
                thesis = buildThesisXml(it.thesis)
            )
            postToCienciaVitae(baseUrl, "degree", payload, "education")
        }
    }

    private fun buildThesisXml(thesis: Thesis?): ThesisXml? {
        return thesis?.let {
            val supervisorsXml = if (it.supervisors.isNotEmpty()) {
                val supervisors = mutableListOf<SupervisorXml>()
                it.supervisors.forEach{supervisor ->
                    supervisors.add(
                        SupervisorXml(
                            supervisorName = supervisor.name,
                            supervisorRole = CodeValueXml(supervisor.role.code, supervisor.role.value)
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
            val authors = projectAuthorRepository.findByProject(it).map { projectAuthor ->  projectAuthor.author }
            val fundingIdentifiers = it.identifier?.let { id ->
                FundingIdentifiersXml(
                    total = 1,
                    identifiers = listOf(FundingIdentifierXml(reference = id))
                )
            }
            val payload = ProjectXml(
                fundingCategory = CodeValueXml(it.fundingType.code, it.fundingType.name),
                title = it.title,
                description = it.description,
                startDate = buildDateXml(it.startDate)!!,
                endDate = buildDateXml(it.endDate),
                status = CodeValueXml(it.state?.code, it.state?.name),
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
            postToCienciaVitae(baseUrl, "output", payload, "production")
        }
    }

    private fun buildDetailedOutputBlock(it: Production): OutputXml {
        val type = it.type
        val outputCategory = CodeValueXml(type.categoryCode, type.categoryName)
        val outputType = CodeValueXml(type.code, type.value)
        val identifiersXml = buildIdentifiersXml(it.doi, it.isbn, it.issn)
        val authors = productionAuthorRepository.findByProduction(it).map { productionAuthor ->  productionAuthor.author }
        val authorsXml = buildAuthorsXml(authors)

        return when (type) {
            ProductionType.JOURNAL_ARTICLE -> OutputXml(
                outputCategory = outputCategory,
                outputType = outputType,
                journalArticle = JournalArticleXml(it.title, it.secondaryTitle, it.volume, it.pageFrom, it.pageTo,
                    buildDateXml(it.date)!!, it.url, identifiersXml, authorsXml)
            )

            ProductionType.BOOK -> OutputXml(
                outputCategory = outputCategory,
                outputType = outputType,
                book = BookXml(it.title, it.volume, it.date.year, it.url, identifiersXml, authorsXml)
            )

            ProductionType.EDITED_BOOK -> OutputXml(
                outputCategory = outputCategory,
                outputType = outputType,
                editedBook = EditedBookXml(it.title, it.volume, it.date.year, it.url, identifiersXml, authorsXml)
            )

            ProductionType.BOOK_CHAPTER -> OutputXml(
                outputCategory = outputCategory,
                outputType = outputType,
                bookChapter = BookChapterXml(it.title, it.secondaryTitle, it.volume, it.pageFrom, it.pageTo,
                    it.date.year, it.url, identifiersXml, authorsXml)
            )

            ProductionType.TRANSLATION -> OutputXml(
                outputCategory = outputCategory,
                outputType = outputType,
                bookChapter = BookChapterXml(it.title, it.secondaryTitle, it.volume, it.pageFrom, it.pageTo,
                    it.date.year, it.url, identifiersXml, authorsXml)
            )

            ProductionType.NEWSPAPER_ARTICLE -> OutputXml(
                outputCategory = outputCategory,
                outputType = outputType,
                newspaperArticle = NewspaperArticleXml(it.title, it.secondaryTitle, it.volume, it.pageFrom, it.pageTo,
                    buildDateXml(it.date)!!, it.url, identifiersXml, authorsXml)
            )

            ProductionType.MAGAZINE_ARTICLE -> OutputXml(
                outputCategory = outputCategory,
                outputType = outputType,
                magazineArticle = MagazineArticleXml(it.title, it.secondaryTitle, it.volume, it.pageFrom, it.pageTo,
                    buildDateXml(it.date)!!, it.url, identifiersXml, authorsXml)
            )

            ProductionType.REPORT -> OutputXml(
                outputCategory = outputCategory,
                outputType = outputType,
                report = ReportXml(it.title, it.volume, buildDateXml(it.date)!!, it.url, identifiersXml, authorsXml)
            )

            ProductionType.WEBSITE -> OutputXml(
                outputCategory = outputCategory,
                outputType = outputType,
                website = WebsiteXml(it.title, it.description, buildDateXml(it.date)!!,
                    it.url, identifiersXml, authorsXml)
            )

            ProductionType.PATENT -> OutputXml(
                outputCategory = outputCategory,
                outputType = outputType,
                patent = PatentXml(it.title, buildDateXml(it.date)!!, identifiersXml, authorsXml)
            )

            ProductionType.RESEARCH_TECHNIQUE -> OutputXml(
                outputCategory = outputCategory,
                outputType = outputType,
                researchTechnique = ResearchTechniqueXml(it.title, buildDateXml(it.date)!!, identifiersXml, authorsXml)
            )

            ProductionType.SOFTWARE -> OutputXml(
                outputCategory = outputCategory,
                outputType = outputType,
                software = SoftwareXml(it.title, it.description, it.version!!, it.platform!!,
                    buildDateXml(it.date)!!, identifiersXml, authorsXml)
            )

            ProductionType.OTHER_OUTPUT -> OutputXml(
                outputCategory = outputCategory,
                outputType = outputType,
                otherOutput = OtherOutputXml(it.title, it.description, buildDateXml(it.date)!!,
                    it.url, identifiersXml, authorsXml)
            )
        }
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