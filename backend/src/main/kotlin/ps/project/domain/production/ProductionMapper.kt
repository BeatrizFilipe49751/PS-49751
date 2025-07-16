package ps.project.domain.production

import ps.project.domain.User

object ProductionMapper {

    /* ------------  DTO ➜ Entity  ------------ */
    fun toEntity(dto: ProductionDTO, user: User): Production? {
        return when (dto.type) {
            ProductionType.BOOK -> Book(
                id = dto.id ?: 0,
                title = dto.title,
                date = dto.date,
                user = user,
                doi = dto.doi,
                isbn = dto.isbn,
                volume = dto.volume,
                url = dto.url
            )
            ProductionType.EDITED_BOOK -> EditedBook(
                id = dto.id ?: 0,
                title = dto.title,
                date = dto.date,
                user = user,
                doi = dto.doi,
                isbn = dto.isbn,
                volume = dto.volume,
                url = dto.url
            )
            ProductionType.JOURNAL_ARTICLE -> Journal(
                id = dto.id ?: 0,
                title = dto.title,
                date = dto.date,
                user = user,
                doi = dto.doi,
                pageFrom = dto.pageFrom,
                pageTo = dto.pageTo,
                secondaryTitle = dto.secondaryTitle,
                volume = dto.volume,
                url = dto.url,
                issn = dto.issn
            )
            ProductionType.MAGAZINE_ARTICLE -> Magazine(
                id = dto.id ?: 0,
                title = dto.title,
                date = dto.date,
                user = user,
                doi = dto.doi,
                pageFrom = dto.pageFrom,
                pageTo = dto.pageTo,
                secondaryTitle = dto.secondaryTitle,
                volume = dto.volume,
                url = dto.url,
                issn = dto.issn
            )
            ProductionType.NEWSPAPER_ARTICLE -> Newspaper(
                id = dto.id ?: 0,
                title = dto.title,
                date = dto.date,
                user = user,
                doi = dto.doi,
                pageFrom = dto.pageFrom,
                pageTo = dto.pageTo,
                secondaryTitle = dto.secondaryTitle,
                volume = dto.volume,
                url = dto.url,
                issn = dto.issn
            )
            ProductionType.TRANSLATION -> Translation(
                id = dto.id ?: 0,
                title = dto.title,
                date = dto.date,
                user = user,
                doi = dto.doi,
                pageFrom = dto.pageFrom,
                pageTo = dto.pageTo,
                secondaryTitle = dto.secondaryTitle,
                volume = dto.volume,
                url = dto.url
            )
            ProductionType.BOOK_CHAPTER -> BookChapter(
                id = dto.id ?: 0,
                title = dto.title,
                date = dto.date,
                user = user,
                doi = dto.doi,
                pageFrom = dto.pageFrom,
                pageTo = dto.pageTo,
                secondaryTitle = dto.secondaryTitle,
                volume = dto.volume,
                url = dto.url,
                isbn = dto.isbn
            )
            ProductionType.SOFTWARE -> Software(
                id = dto.id ?: 0,
                title = dto.title,
                date = dto.date,
                user = user,
                description = dto.description,
                version = dto.version,
                platform = dto.platform,
                doi = dto.doi
            )
            ProductionType.REPORT -> Report(
                id = dto.id ?: 0,
                title = dto.title,
                date = dto.date,
                user = user,
                url = dto.url,
                volume = dto.volume,
                doi = dto.doi
            )
            ProductionType.PATENT -> Patent(
                id = dto.id ?: 0,
                title = dto.title,
                date = dto.date,
                user = user
            )
            ProductionType.RESEARCH_TECHNIQUE -> ResearchTechnique(
                id = dto.id ?: 0,
                title = dto.title,
                date = dto.date,
                user = user,
                doi = dto.doi
            )
            ProductionType.OTHER_OUTPUT -> Other(
                id = dto.id ?: 0,
                title = dto.title,
                date = dto.date,
                user = user,
                description = dto.description,
                url = dto.url
            )
            ProductionType.WEBSITE -> WebsiteProd(
                id = dto.id ?: 0,
                title = dto.title,
                date = dto.date,
                user = user,
                url = dto.url,
                description = dto.description,
            )
            else -> null
        }
    }

    /* ------------  Entity ➜ DTO  ------------ */
    fun toDTO(entity: Production, authors: List<String> = emptyList()): ProductionDTO? = when (entity) {
        is Book -> ProductionDTO(
            id = entity.id, type = ProductionType.BOOK,
            title = entity.title, date = entity.date,
            doi = entity.doi, isbn = entity.isbn,
            volume = entity.volume, url = entity.url,
            authors = authors
        )

        is EditedBook -> ProductionDTO(
            id = entity.id, type = ProductionType.EDITED_BOOK,
            title = entity.title, date = entity.date,
            doi = entity.doi, isbn = entity.isbn,
            volume = entity.volume, url = entity.url,
            authors = authors
        )

        is Journal -> ProductionDTO(
            id = entity.id, type = ProductionType.JOURNAL_ARTICLE,
            title = entity.title, date = entity.date,
            doi = entity.doi, pageFrom = entity.pageFrom, pageTo = entity.pageTo,
            secondaryTitle = entity.secondaryTitle, volume = entity.volume,
            url = entity.url, issn = entity.issn,
            authors = authors
        )

        is Magazine -> ProductionDTO(
            id = entity.id, type = ProductionType.MAGAZINE_ARTICLE,
            title = entity.title, date = entity.date,
            doi = entity.doi, pageFrom = entity.pageFrom, pageTo = entity.pageTo,
            secondaryTitle = entity.secondaryTitle, volume = entity.volume,
            url = entity.url, issn = entity.issn,
            authors = authors
        )

        is Newspaper -> ProductionDTO(
            id = entity.id, type = ProductionType.NEWSPAPER_ARTICLE,
            title = entity.title, date = entity.date,
            doi = entity.doi, pageFrom = entity.pageFrom, pageTo = entity.pageTo,
            secondaryTitle = entity.secondaryTitle, volume = entity.volume,
            url = entity.url, issn = entity.issn,
            authors = authors
        )

        is Translation -> ProductionDTO(
            id = entity.id, type = ProductionType.TRANSLATION,
            title = entity.title, date = entity.date,
            doi = entity.doi, pageFrom = entity.pageFrom, pageTo = entity.pageTo,
            secondaryTitle = entity.secondaryTitle, volume = entity.volume,
            url = entity.url, authors = authors
        )

        is BookChapter -> ProductionDTO(
            id = entity.id, type = ProductionType.BOOK_CHAPTER,
            title = entity.title, date = entity.date,
            doi = entity.doi, pageFrom = entity.pageFrom, pageTo = entity.pageTo,
            secondaryTitle = entity.secondaryTitle, volume = entity.volume,
            url = entity.url, isbn = entity.isbn, authors = authors
        )

        is Software -> ProductionDTO(
            id = entity.id, type = ProductionType.SOFTWARE,
            title = entity.title, date = entity.date,
            description = entity.description, version = entity.version,
            platform = entity.platform, doi = entity.doi, authors = authors
        )

        is Report -> ProductionDTO(
            id = entity.id, type = ProductionType.REPORT,
            title = entity.title, date = entity.date,
            url = entity.url, volume = entity.volume, doi = entity.doi,
            authors = authors
        )

        is Patent -> ProductionDTO(
            id = entity.id, type = ProductionType.PATENT,
            title = entity.title, date = entity.date,
            authors = authors
        )

        is ResearchTechnique -> ProductionDTO(
            id = entity.id, type = ProductionType.RESEARCH_TECHNIQUE,
            title = entity.title, date = entity.date,
            doi = entity.doi, authors = authors
        )

        is Thesis -> ProductionDTO(
            id = entity.id, type = ProductionType.THESIS,
            title = entity.title, date = entity.date,
            authors = authors
        )

        is Other -> ProductionDTO(
            id = entity.id, type = ProductionType.OTHER_OUTPUT,
            title = entity.title, date = entity.date,
            description = entity.description, url = entity.url,
            authors = authors
        )

        is WebsiteProd -> ProductionDTO(
            id = entity.id, type = ProductionType.WEBSITE,
            title = entity.title, date = entity.date,
            url = entity.url, description = entity.description,
            authors = authors
        )

        else -> null
    }
}