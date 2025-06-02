package ps.project.mapping.mappers

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.stereotype.Service
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import ps.project.domain.*
import ps.project.domain.activity.ActivityDTO
import ps.project.domain.author.AuthorDTO
import ps.project.domain.contact.*
import ps.project.domain.distinction.DistinctionDTO
import ps.project.domain.education.*
import ps.project.domain.identifier.IdentifierDTO
import ps.project.domain.identifier.IdentifierType
import ps.project.domain.language.LanguageDTO
import ps.project.domain.production.ProductionDTO
import ps.project.domain.production.ProductionType
import ps.project.domain.profExp.ProfessionalExperienceDTO
import ps.project.domain.project.FundingType
import ps.project.domain.project.ProjectDTO
import ps.project.mapping.xml.lattes.EmptyStringAsNullModule
import ps.project.mapping.xml.lattes.GeneralData
import ps.project.mapping.xml.lattes.LattesCVModel
import ps.project.mapping.xml.lattes.address.LattesAddressDetailsModel
import java.io.ByteArrayInputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.xml.parsers.DocumentBuilderFactory

@Service
class LattesMapper : CvMapper {
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
        val contacts = extractContacts(data)

        // TODO - to remove this
        val document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            .parse(ByteArrayInputStream(xmlString.toByteArray()))

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
            productions = extractProductions(document),
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

    private fun extractContacts(data: GeneralData?): ContactDTO {
        val dataAddresses = data?.addresses ?: return ContactDTO()

        val addresses = mutableListOf<AddressDTO>()
        val emails = mutableListOf<EmailDTO>()
        val phones = mutableListOf<PhoneDTO>()
        val websites = mutableListOf<WebsiteDTO>()

        dataAddresses.forEach { address ->
            address.professional?.let { extractContactDetails(it, ContactType.Professional, addresses, emails, phones, websites) }
            address.residential?.let { extractContactDetails(it, ContactType.Personal, addresses, emails, phones, websites) }
        }

        return ContactDTO(
            emails = emails,
            addresses = addresses,
            phones = phones,
            websites = websites
        )
    }

    private fun extractContactDetails(
        details: LattesAddressDetailsModel,
        type: ContactType,
        addresses: MutableList<AddressDTO>,
        emails: MutableList<EmailDTO>,
        phones: MutableList<PhoneDTO>,
        websites: MutableList<WebsiteDTO>
    ) {
        val address = toAddressDTO(details, type)
        if (address != null) {
            addresses.add(address)
        }
        details.email?.let { emails.add(EmailDTO(type = type, address = it)) }
        details.phone?.toIntOrNull()?.let { phones.add(PhoneDTO(type = type, number = it, device = PhoneDevice.Telephone)) }
        details.fax?.toIntOrNull()?.let { phones.add(PhoneDTO(type = type, number = it, device = PhoneDevice.Fax)) }
        details.homepage?.let { websites.add(WebsiteDTO(type = type.toWebsiteType(), url = it)) }
    }

    private fun toAddressDTO(details: LattesAddressDetailsModel, type: ContactType): AddressDTO? {
        val address = details.streetComplement ?: details.street ?: return null
        return AddressDTO(
            type = type,
            address = address,
            zipCode = details.zipCode,
            locality = details.neighborhood,
            municipality = details.city,
            country = details.country
        )
    }

    private fun ContactType.toWebsiteType(): WebsiteType {
        return when (this) {
            ContactType.Professional -> WebsiteType.Professional
            ContactType.Personal -> WebsiteType.Personal
        }
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

                val thesis = thesisTitle?.let {
                    ThesisDTO(title = it, supervisors = supervisors)
                }

                educations.add(
                    EducationDTO(
                        degree = level,
                        course = course,
                        institution = institution,
                        status = status,
                        courseCode = courseCode,
                        startDate = startDate,
                        endDate = endDate,
                        thesis = thesis
                    )
                )
            }
        }
        return educations
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
                    startDate = startDate,
                    endDate = endDate,
                    description = project.description,
                    identifier = project.identifier,
                    fundingType = project.funding?.funding?.mapFundingType() ?: FundingType.Other,
                    state = project.mapState(),
                    authors = authors
                )
            }
        }
    }

    // TODO - use Jackson XML instead
    private fun extractProductions(document: Document): List<ProductionDTO> {
        return extractArticles(document)+extractBooks(document)+extractChapters(document)+extractOthers(document)+
                extractOthers2(document)+extractPatents(document)+extractReports(document)+
                extractReports2(document)+extractSoftwares(document)+extractTechniques(document)+extractTexts(document)+
                extractTranslations(document)+extractWebsites(document)
    }

    private fun extractOthers2(document: Document): List<ProductionDTO> {
        val otherNodes = document.getElementsByTagName("OUTRA-PRODUCAO-TECNICA")
        val productions = mutableListOf<ProductionDTO>()

        for (i in 0 until otherNodes.length) {
            val otherNode = otherNodes.item(i)
            val dataNode = (otherNode as Element).getElementsByTagName("DADOS-BASICOS-DE-OUTRA-PRODUCAO-TECNICA").item(0)

            val title = extractValue("TITULO", dataNode) ?: continue
            val type = ProductionType.OTHER_OUTPUT
            val year = extractValue("ANO", dataNode)?.toIntOrNull() ?: continue
            val date = year.let { LocalDate.of(it, 1, 1) }
            val doi = extractValue("DOI", dataNode)
            val url = extractValue("HOME-PAGE-DO-TRABALHO", dataNode)

            val detailsNode = otherNode.getElementsByTagName("DETALHAMENTO-DE-OUTRA-PRODUCAO-TECNICA").item(0)

            val desc = extractValue("FINALIDADE", detailsNode)

            val authors = extractProductionAuthors(otherNode)

            productions.add(
                ProductionDTO(
                    title = title,
                    type = type,
                    date = date,
                    description = desc,
                    doi = doi,
                    url = url,
                    authors = authors
                )
            )
        }
        return productions
    }


    private fun extractWebsites(document: Document): List<ProductionDTO> {
        val websiteNodes = document.getElementsByTagName("MIDIA-SOCIAL-WEBSITE-BLOG")
        val productions = mutableListOf<ProductionDTO>()

        for (i in 0 until websiteNodes.length) {
            val websiteNode = websiteNodes.item(i)
            val dataNode = (websiteNode as Element).getElementsByTagName("DADOS-BASICOS-DA-MIDIA-SOCIAL-WEBSITE-BLOG").item(0)

            val title = extractValue("TITULO", dataNode) ?: continue
            val type = ProductionType.WEBSITE
            val year = extractValue("ANO", dataNode)?.toIntOrNull() ?: continue
            val date = year.let { LocalDate.of(it, 1, 1) }
            val url = extractValue("HOME-PAGE", dataNode)

            val detailsNode = websiteNode.getElementsByTagName("DETALHAMENTO-DA-MIDIA-SOCIAL-WEBSITE-BLOG").item(0)

            val desc = extractValue("TEMA", detailsNode)

            val authors = extractProductionAuthors(websiteNode)

            productions.add(
                ProductionDTO(
                    title = title,
                    type = type,
                    date = date,
                    description = desc,
                    url = url,
                    authors = authors
                )
            )
        }
        return productions
    }

    private fun extractReports2(document: Document): List<ProductionDTO> {
        val reportNodes = document.getElementsByTagName("RELATORIO-DE-PESQUISA")
        val productions = mutableListOf<ProductionDTO>()

        for (i in 0 until reportNodes.length) {
            val reportNode = reportNodes.item(i)
            val dataNode = (reportNode as Element).getElementsByTagName("DADOS-BASICOS-DO-RELATORIO-DE-PESQUISA").item(0)

            val type = ProductionType.REPORT
            val title = extractValue("TITULO", dataNode) ?: continue
            val year = extractValue("ANO", dataNode)?.toIntOrNull() ?: continue
            val date = year.let { LocalDate.of(it, 1, 1) }
            val doi = extractValue("DOI", dataNode)
            val url = extractValue("HOME-PAGE-DO-TRABALHO", dataNode)

            val authors = extractProductionAuthors(reportNode)

            productions.add(
                ProductionDTO(
                    title = title,
                    type = type,
                    date = date,
                    doi = doi,
                    url = url,
                    authors = authors
                )
            )
        }
        return productions
    }

    private fun extractReports(document: Document): List<ProductionDTO> {
        val reportNodes = document.getElementsByTagName("TRABALHO-TECNICO")
        val productions = mutableListOf<ProductionDTO>()

        for (i in 0 until reportNodes.length) {
            val reportNode = reportNodes.item(i)
            val dataNode = (reportNode as Element).getElementsByTagName("DADOS-BASICOS-DO-TRABALHO-TECNICO").item(0)

            val typeValue = extractValue("NATUREZA", dataNode) ?: continue
            val type = if (typeValue == "RELATORIO_TECNICO") ProductionType.REPORT else continue
            val title = extractValue("TITULO-DO-TRABALHO-TECNICO", dataNode) ?: continue
            val year = extractValue("ANO", dataNode)?.toIntOrNull() ?: continue
            val date = year.let { LocalDate.of(it, 1, 1) }
            val doi = extractValue("DOI", dataNode)
            val url = extractValue("HOME-PAGE-DO-TRABALHO", dataNode)

            val authors = extractProductionAuthors(reportNode)

            productions.add(
                ProductionDTO(
                    title = title,
                    type = type,
                    date = date,
                    doi = doi,
                    url = url,
                    authors = authors
                )
            )
        }
        return productions
    }

    private fun extractTechniques(document: Document): List<ProductionDTO> {
        val techniqueNodes = document.getElementsByTagName("PROCESSOS-OU-TECNICAS")
        val productions = mutableListOf<ProductionDTO>()

        for (i in 0 until techniqueNodes.length) {
            val techniqueNode = techniqueNodes.item(i)
            val dataNode = (techniqueNode as Element).getElementsByTagName("DADOS-BASICOS-DO-PROCESSOS-OU-TECNICAS").item(0)

            val title = extractValue("TITULO-DO-PROCESSO", dataNode) ?: continue
            val type = ProductionType.RESEARCH_TECHNIQUE
            val year = extractValue("ANO", dataNode)?.toIntOrNull() ?: continue
            val date = year.let { LocalDate.of(it, 1, 1) }
            val doi = extractValue("DOI", dataNode)
            val url = extractValue("HOME-PAGE-DO-TRABALHO", dataNode)

            val authors = extractProductionAuthors(techniqueNode)

            productions.add(
                ProductionDTO(
                    title = title,
                    type = type,
                    date = date,
                    doi = doi,
                    url = url,
                    authors = authors
                )
            )
        }
        return productions
    }

    private fun extractSoftwares(document: Document): List<ProductionDTO> {
        val softwareNodes = document.getElementsByTagName("SOFTWARE")
        val productions = mutableListOf<ProductionDTO>()

        for (i in 0 until softwareNodes.length) {
            val softwareNode = softwareNodes.item(i)
            val dataNode = (softwareNode as Element).getElementsByTagName("DADOS-BASICOS-DO-SOFTWARE").item(0)

            val title = extractValue("TITULO-DO-SOFTWARE", dataNode) ?: continue
            val type = ProductionType.SOFTWARE
            val year = extractValue("ANO", dataNode)?.toIntOrNull() ?: continue
            val date = year.let { LocalDate.of(it, 1, 1) }
            val doi = extractValue("DOI", dataNode)
            val url = extractValue("HOME-PAGE-DO-TRABALHO", dataNode)

            val detailsNode = softwareNode.getElementsByTagName("DETALHAMENTO-DO-SOFTWARE").item(0)

            val platform = extractValue("PLATAFORMA", detailsNode)
            val version = "1"

            val authors = extractProductionAuthors(softwareNode)

            productions.add(
                ProductionDTO(
                    title = title,
                    type = type,
                    date = date,
                    doi = doi,
                    url = url,
                    version = version,
                    platform = platform,
                    authors = authors
                )
            )
        }
        return productions
    }

    private fun extractPatents(document: Document): List<ProductionDTO> {
        val patentNodes = document.getElementsByTagName("REGISTRO-OU-PATENTE")
        val productions = mutableListOf<ProductionDTO>()

        for (i in 0 until patentNodes.length) {
            val patentNode = patentNodes.item(i)

            val title = extractValue("TITULO-PATENTE", patentNode) ?: continue
            val type = ProductionType.PATENT
            val dateValue = extractValue("DATA-DE-CONCESSAO", patentNode) ?: continue
            val formatter = DateTimeFormatter.ofPattern("ddMMyyyy")
            val date = LocalDate.parse(dateValue, formatter)

            productions.add(
                ProductionDTO(
                    title = title,
                    type = type,
                    date = date,
                )
            )
        }
        return productions
    }

    private fun extractTranslations(document: Document): List<ProductionDTO> {
        val translationNodes = document.getElementsByTagName("TRADUCAO")
        val productions = mutableListOf<ProductionDTO>()

        for (i in 0 until translationNodes.length) {
            val translationNode = translationNodes.item(i)
            val dataNode = (translationNode as Element).getElementsByTagName("DADOS-BASICOS-DA-TRADUCAO").item(0)

            val title = extractValue("TITULO", dataNode) ?: continue
            val type = ProductionType.TRANSLATION
            val year = extractValue("ANO", dataNode)?.toIntOrNull() ?: continue
            val date = year.let { LocalDate.of(it, 1, 1) }
            val doi = extractValue("DOI", dataNode)
            val url = extractValue("HOME-PAGE-DO-TRABALHO", dataNode)

            val detailsNode = translationNode.getElementsByTagName("DETALHAMENTO-DA-TRADUCAO").item(0)

            val volume = extractValue("VOLUME", detailsNode)?.toIntOrNull()

            val authors = extractProductionAuthors(translationNode)

            productions.add(
                ProductionDTO(
                    title = title,
                    type = type,
                    date = date,
                    doi = doi,
                    url = url,
                    volume = volume,
                    authors = authors
                )
            )
        }
        return productions
    }

    private fun extractOthers(document: Document): List<ProductionDTO> {
        val otherNodes = document.getElementsByTagName("OUTRA-PRODUCAO-BIBLIOGRAFICA")
        val productions = mutableListOf<ProductionDTO>()

        for (i in 0 until otherNodes.length) {
            val otherNode = otherNodes.item(i)
            val dataNode = (otherNode as Element).getElementsByTagName("DADOS-BASICOS-DE-OUTRA-PRODUCAO").item(0)

            val title = extractValue("TITULO", dataNode) ?: continue
            val type = ProductionType.OTHER_OUTPUT
            val year = extractValue("ANO", dataNode)?.toIntOrNull() ?: continue
            val date = year.let { LocalDate.of(it, 1, 1) }
            val doi = extractValue("DOI", dataNode)
            val url = extractValue("HOME-PAGE-DO-TRABALHO", dataNode)

            val authors = extractProductionAuthors(otherNode)

            productions.add(
                ProductionDTO(
                    title = title,
                    type = type,
                    date = date,
                    doi = doi,
                    url = url,
                    authors = authors
                )
            )
        }
        return productions
    }

    private fun extractTexts(document: Document): List<ProductionDTO> {
        val textNodes = document.getElementsByTagName("TEXTO-EM-JORNAL-OU-REVISTA")
        val productions = mutableListOf<ProductionDTO>()

        for (i in 0 until textNodes.length) {
            val textNode = textNodes.item(i)
            val dataNode = (textNode as Element).getElementsByTagName("DADOS-BASICOS-DO-TEXTO").item(0)

            val title = extractValue("TITULO-DO-TEXTO", dataNode) ?: continue
            val type = mapTextType(extractValue("NATUREZA", dataNode))
            val doi = extractValue("DOI", dataNode)
            val url = extractValue("HOME-PAGE-DO-TRABALHO", dataNode)

            val detailsNode = textNode.getElementsByTagName("DETALHAMENTO-DO-TEXTO").item(0)

            val secondaryTitle = extractValue("TITULO-DO-JORNAL-OU-REVISTA", detailsNode)
            val issn = extractValue("ISSN", detailsNode)
            val dateValue = extractValue("DATA-DE-PUBLICACAO", detailsNode) ?: continue
            val formatter = DateTimeFormatter.ofPattern("ddMMyyyy")
            val date = LocalDate.parse(dateValue, formatter)
            val volume = extractValue("VOLUME", detailsNode)?.toIntOrNull()
            val pageFrom = extractValue("PAGINA-INICIAL", detailsNode)?.toIntOrNull()
            val pageTo = extractValue("PAGINA-FINAL", detailsNode)?.toIntOrNull()

            val authors = extractProductionAuthors(textNode)

            productions.add(
                ProductionDTO(
                    title = title,
                    type = type,
                    date = date,
                    doi = doi,
                    issn = issn,
                    url = url,
                    secondaryTitle = secondaryTitle,
                    volume = volume,
                    pageFrom = pageFrom,
                    pageTo = pageTo,
                    authors = authors
                )
            )
        }
        return productions
    }

    private fun extractChapters(document: Document): List<ProductionDTO> {
        val chapterNodes = document.getElementsByTagName("CAPITULO-DE-LIVRO-PUBLICADO")
        val productions = mutableListOf<ProductionDTO>()

        for (i in 0 until chapterNodes.length) {
            val chapterNode = chapterNodes.item(i)
            val dataNode = (chapterNode as Element).getElementsByTagName("DADOS-BASICOS-DO-CAPITULO").item(0)

            val title = extractValue("TITULO-DO-CAPITULO-DO-LIVRO", dataNode) ?: continue
            val type = ProductionType.BOOK_CHAPTER
            val year = extractValue("ANO", dataNode)?.toIntOrNull() ?: continue
            val date = year.let { LocalDate.of(it, 1, 1) }
            val doi = extractValue("DOI", dataNode)
            val url = extractValue("HOME-PAGE-DO-TRABALHO", dataNode)

            val detailsNode = chapterNode.getElementsByTagName("DETALHAMENTO-DO-CAPITULO").item(0)

            val secondaryTitle = extractValue("TITULO-DO-LIVRO", detailsNode)
            val isbn = extractValue("ISBN", detailsNode)
            val pageFrom = extractValue("PAGINA-INICIAL", detailsNode)?.toIntOrNull()
            val pageTo = extractValue("PAGINA-FINAL", detailsNode)?.toIntOrNull()

            val authors = extractProductionAuthors(chapterNode)

            productions.add(
                ProductionDTO(
                    title = title,
                    type = type,
                    date = date,
                    doi = doi,
                    isbn = isbn,
                    url = url,
                    secondaryTitle = secondaryTitle,
                    pageFrom = pageFrom,
                    pageTo = pageTo,
                    authors = authors
                )
            )
        }
        return productions
    }

    private fun extractBooks(document: Document): List<ProductionDTO> {
        val bookNodes = document.getElementsByTagName("LIVRO-PUBLICADO-OU-ORGANIZADO")
        val productions = mutableListOf<ProductionDTO>()

        for (i in 0 until bookNodes.length) {
            val bookNode = bookNodes.item(i)
            val dataNode = (bookNode as Element).getElementsByTagName("DADOS-BASICOS-DO-LIVRO").item(0)

            val title = extractValue("TITULO-DO-LIVRO", dataNode) ?: continue
            val type = mapBookType(extractValue("TIPO", dataNode))
            val year = extractValue("ANO", dataNode)?.toIntOrNull() ?: continue
            val date = year.let { LocalDate.of(it, 1, 1) }
            val doi = extractValue("DOI", dataNode)
            val url = extractValue("HOME-PAGE-DO-TRABALHO", dataNode)

            val detailsNode = bookNode.getElementsByTagName("DETALHAMENTO-DO-LIVRO").item(0)

            val isbn = extractValue("ISBN", detailsNode)

            val authors = extractProductionAuthors(bookNode)

            productions.add(
                ProductionDTO(
                    title = title,
                    type = type,
                    date = date,
                    doi = doi,
                    isbn = isbn,
                    url = url,
                    authors = authors
                )
            )
        }
        return productions
    }

    private fun extractArticles(document: Document) : List<ProductionDTO> {
        val article = document.getElementsByTagName("ARTIGO-PUBLICADO")
        val productions = mutableListOf<ProductionDTO>()

        for (i in 0 until article.length) {
            val articleNode = (article.item(i)) as Element
            val dataNode = articleNode.getElementsByTagName("DADOS-BASICOS-DO-ARTIGO").item(0)

            val title = extractValue("TITULO-DO-ARTIGO", dataNode) ?: continue
            val type = ProductionType.JOURNAL_ARTICLE
            val year = extractValue("ANO-DO-ARTIGO", dataNode)?.toIntOrNull() ?: continue
            val date = year.let { LocalDate.of(it, 1, 1) }
            val doi = extractValue("DOI", dataNode)
            val url = extractValue("HOME-PAGE-DO-TRABALHO", dataNode)

            val detailsNode = articleNode.getElementsByTagName("DETALHAMENTO-DO-ARTIGO").item(0)

            val issn = extractValue("ISSN", detailsNode)
            val secondaryTitle = extractValue("TITULO-DO-PERIODICO-OU-REVISTA", detailsNode)
            val volume = extractValue("VOLUME", detailsNode)?.toIntOrNull()
            val pageFrom = extractValue("PAGINA-INICIAL", detailsNode)?.toIntOrNull()
            val pageTo = extractValue("PAGINA-FINAL", detailsNode)?.toIntOrNull()

            val authors = extractProductionAuthors(articleNode)

            productions.add(
                ProductionDTO(
                    title = title,
                    type = type,
                    date = date,
                    doi = doi,
                    issn = issn,
                    url = url,
                    secondaryTitle = secondaryTitle,
                    volume = volume,
                    pageFrom = pageFrom,
                    pageTo = pageTo,
                    authors = authors
                )
            )
        }
        return productions
    }

    private fun extractProductionAuthors(mainNode: Element): List<String> {
        val authorNodes = mainNode.getElementsByTagName("AUTORES")
        val authors = mutableListOf<String>()
        for (j in 0 until authorNodes.length) {
            val authorNode = authorNodes.item(j)
            val author = extractValue("NOME-PARA-CITACAO", authorNode) ?: continue
            authors.add(author)
        }
        return authors
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

    private fun extractValue(item: String, node: Node): String? {
        return node.attributes?.getNamedItem(item)?.nodeValue?.takeIf { it.isNotBlank() }
    }

    private fun mapBookType(type: String?): ProductionType {
        return when (type?.uppercase()) {
            "LIVRO_PUBLICADO" -> ProductionType.BOOK
            "LIVRO_ORGANIZADO_OU_EDICAO" -> ProductionType.EDITED_BOOK
            else -> ProductionType.BOOK
        }
    }

    private fun mapTextType(type: String?): ProductionType {
        return when (type?.uppercase()) {
            "JORNAL_DE_NOTICIAS" -> ProductionType.NEWSPAPER_ARTICLE
            "REVISTA_MAGAZINE" -> ProductionType.MAGAZINE_ARTICLE
            else -> ProductionType.MAGAZINE_ARTICLE
        }
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
