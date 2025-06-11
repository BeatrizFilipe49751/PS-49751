package ps.project.mapping.mappers.lattes

import org.springframework.stereotype.Component
import ps.project.domain.production.ProductionDTO
import ps.project.domain.production.ProductionType
import ps.project.mapping.xml.lattes.BibliographicProduction
import ps.project.mapping.xml.lattes.LattesCVModel
import ps.project.mapping.xml.lattes.TeamMember
import ps.project.mapping.xml.lattes.TechnicalProduction
import ps.project.mapping.xml.lattes.production.LattesCommonDataProductionXml
import ps.project.mapping.xml.lattes.production.LattesPatentXml
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class LattesProductionMapper {
    fun extractProductions(cv: LattesCVModel): List<ProductionDTO> = sequence {
        cv.technicalProduction?.let {
            yieldAll(extractOtherTechnicalProduction(it))
            yieldAll(extractWebsites(it))
            yieldAll(extractResearchReports(it))
            yieldAll(extractSoftwares(it))
            yieldAll(extractReports(it))
            yieldAll(extractTechniques(it))
        }
        cv.bibliographicProduction?.let {
            yieldAll(extractOtherBibliographicProduction(it))
            yieldAll(extractTranslations(it))
            yieldAll(extractTexts(it))
            yieldAll(extractArticles(it))
            yieldAll(extractChapters(it))
            yieldAll(extractBooks(it))
        }
    }.toList()

    private fun extractOtherBibliographicProduction(production: BibliographicProduction): List<ProductionDTO> {
        val productions = mutableListOf<ProductionDTO>()
        val others = production.other?.others ?: return productions
        val type = ProductionType.OTHER_OUTPUT
        for (other in others) {
            val data = other.basicData
            productions.add(
                extractCommonProduction(data, type, other.authors)
            )
        }
        return productions
    }

    private fun extractOtherTechnicalProduction(production: TechnicalProduction): List<ProductionDTO> {
        val productions = mutableListOf<ProductionDTO>()
        val others = production.other?.others ?: return productions
        val type = ProductionType.OTHER_OUTPUT
        for (other in others) {
            val data = other.basicData
            val patent = extractPatent(other.patent)
            if (patent != null) productions.add(patent)
            productions.add(
                ProductionDTO(
                    title = data.title, type = type, date = yearToDate(data.year), description = other.details.desc,
                    doi = data.doi, url = data.url, authors = extractProductionAuthors(other.authors)
                )
            )
        }
        return productions
    }

    private fun extractWebsites(production: TechnicalProduction): List<ProductionDTO> {
        val productions = mutableListOf<ProductionDTO>()
        val websites = production.other?.websites ?: return productions
        val type = ProductionType.WEBSITE
        for (website in websites) {
            val data = website.basicData
            productions.add(
                ProductionDTO(
                    title = data.title, type = type, date = yearToDate(data.year), description = website.details?.theme,
                    url = data.url, authors = extractProductionAuthors(website.authors)
                )
            )
        }
        return productions
    }

    private fun extractResearchReports(production: TechnicalProduction): List<ProductionDTO> {
        val productions = mutableListOf<ProductionDTO>()
        val reports = production.other?.reports ?: return productions
        val type = ProductionType.REPORT
        for (report in reports) {
            val data = report.basicData
            productions.add(
                extractCommonProduction(data, type, report.authors)
            )
        }
        return productions
    }

    private fun extractReports(production: TechnicalProduction): List<ProductionDTO> {
        val productions = mutableListOf<ProductionDTO>()
        val reports = production.reports ?: return productions

        for (report in reports) {
            val data = report.basicData
            val type = if (data.type == "RELATORIO_TECNICO") ProductionType.REPORT else continue
            productions.add(
                ProductionDTO(
                    title = data.title, type = type, date = yearToDate(data.year), doi = data.doi, url = data.url,
                    authors = extractProductionAuthors(report.authors)
                )
            )
        }
        return productions
    }

    private fun extractTechniques(production: TechnicalProduction): List<ProductionDTO> {
        val productions = mutableListOf<ProductionDTO>()
        val type = ProductionType.RESEARCH_TECHNIQUE
        val techniques = production.researchTechniques ?: return productions

        techniques.forEach { technique ->
            val data = technique.basicData
            productions.add(
                ProductionDTO(
                    title = data.title, type = type, date = yearToDate(data.year), doi = data.doi, url = data.url,
                    authors = extractProductionAuthors(technique.authors)
                )
            )
        }
        return productions
    }

    private fun extractSoftwares(production: TechnicalProduction): List<ProductionDTO> {
        val productions = mutableListOf<ProductionDTO>()
        val type = ProductionType.SOFTWARE
        val softwares = production.softwares ?: return productions

        softwares.forEach { software ->
            val data = software.basicData
            val details = software.details
            val patent = extractPatent(details.patent)
            if (patent != null) productions.add(patent)
            productions.add(
                ProductionDTO(
                    title = data.title, type = type, date = yearToDate(data.year), doi = data.doi, url = data.url,
                    version = "1", platform = details.platform, authors = extractProductionAuthors(software.authors)
                )
            )
        }
        return productions
    }

    private fun extractTranslations(production: BibliographicProduction): List<ProductionDTO> {
        val productions = mutableListOf<ProductionDTO>()
        val translations = production.other?.translations ?: return productions
        val type = ProductionType.TRANSLATION
        for (translation in translations) {
            val data = translation.basicData
            productions.add(
                ProductionDTO(
                    title = data.title, type = type, date = yearToDate(data.year), doi = data.doi,
                    url = data.url, volume = translation.details?.volume,
                    authors = extractProductionAuthors(translation.authors)
                )
            )
        }
        return productions
    }

    private fun extractTexts(production: BibliographicProduction): List<ProductionDTO> {
        val productions = mutableListOf<ProductionDTO>()
        val texts = production.texts?.texts ?: return productions
        for (text in texts) {
            val data = text.basicData
            val details = text.details
            productions.add(
                ProductionDTO(
                    title = data.title, type = mapTextType(data.nature), date = formatDate(details.publicationDate),
                    doi = data.doi, issn = details.issn, url = data.url, secondaryTitle = details.journalTitle,
                    volume = details.volume, pageFrom = details.pageFrom, pageTo = details.pageTo,
                    authors = extractProductionAuthors(text.authors)
                )
            )
        }
        return productions
    }

    private fun extractArticles(production: BibliographicProduction) : List<ProductionDTO> {
        val productions = mutableListOf<ProductionDTO>()
        val articles = production.articles?.articles ?: return productions
        val type = ProductionType.JOURNAL_ARTICLE
        for (article in articles) {
            val data = article.basicData
            val details = article.details
            productions.add(
                ProductionDTO(
                    title = data.title, type = type, date = yearToDate(data.year), doi = data.doi,
                    issn = details?.issn, url = data.url, secondaryTitle = details?.journalTitle,
                    volume = details?.volume, pageFrom = details?.pageFrom, pageTo = details?.pageTo,
                    authors = extractProductionAuthors(article.authors)
                )
            )
        }
        return productions
    }

    private fun extractChapters(production: BibliographicProduction): List<ProductionDTO> {
        val productions = mutableListOf<ProductionDTO>()
        val chapters = production.booksAndChapters?.chapters?.chapters ?: return productions
        val type = ProductionType.BOOK_CHAPTER
        for (chapter in chapters) {
            val data = chapter.basicData
            val details = chapter.details
            productions.add(
                ProductionDTO(
                    title = data.title, type = type, date = yearToDate(data.year), doi = data.doi,
                    isbn = details?.isbn, url = data.url, secondaryTitle = details?.bookTitle,
                    pageFrom = details?.pageFrom, pageTo = details?.pageTo,
                    authors = extractProductionAuthors(chapter.authors)
                )
            )
        }
        return productions
    }

    private fun extractBooks(production: BibliographicProduction): List<ProductionDTO> {
        val productions = mutableListOf<ProductionDTO>()
        val books = production.booksAndChapters?.books?.books ?: return productions
        for (book in books) {
            val data = book.basicData
            val type = mapBookType(data.type)
            productions.add(
                ProductionDTO(
                    title = data.title, type = type, date = yearToDate(data.year), doi = data.doi,
                    isbn = book.details?.isbn, url = data.url, authors = extractProductionAuthors(book.authors)
                )
            )
        }
        return productions
    }

    private fun extractProductionAuthors(members: List<TeamMember>?): List<String> {
        val authors = mutableListOf<String>()
        members ?: return authors
        for (member in members) {
            val author = member.citationName ?: continue
            authors.add(author)
        }
        return authors
    }

    private fun extractPatent(patent: LattesPatentXml?): ProductionDTO? {
        return if (patent != null) {
            ProductionDTO(
                title = patent.title,
                type = ProductionType.PATENT,
                date = formatDate(patent.date)
            )
        } else {
            null
        }
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

    private fun extractCommonProduction(data: LattesCommonDataProductionXml, type: ProductionType, authors: List<TeamMember>?) =
        ProductionDTO(
            title = data.title,
            type = type,
            date = yearToDate(data.year),
            doi = data.doi,
            url = data.url,
            authors = extractProductionAuthors(authors)
        )

    private fun formatDate(date: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("ddMMyyyy")
        return LocalDate.parse(date, formatter)
    }

    private fun yearToDate(year: Int): LocalDate {
        return year.let { LocalDate.of(it, 1, 1) }
    }
}