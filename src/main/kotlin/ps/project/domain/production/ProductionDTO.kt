package ps.project.domain.production

import ps.project.domain.Cv
import java.time.LocalDate

data class ProductionDTO(
    val id: Int? = null,
    val title: String,
    val type: ProductionType,
    val date: LocalDate,
    val description: String? = null,
    val doi: String? = null,
    val issn: String? = null,
    val isbn: String? = null,
    val url: String? = null,
    val secondaryTitle: String? = null,
    val volume: String? = null,
    val version: String? = null,
    val platform: String? = null,
    val pageFrom: String? = null,
    val pageTo: String? = null,
    val authors: List<String> = emptyList()
){
    fun toEntity(cv: Cv) = Production (
        cv = cv,
        title = title,
        type = type,
        date = date,
        description = description,
        doi = doi,
        issn = issn,
        isbn = isbn,
        url = url,
        secondaryTitle = secondaryTitle,
        volume = volume,
        version = version,
        platform = platform,
        pageFrom = pageFrom,
        pageTo = pageTo
    )
}
