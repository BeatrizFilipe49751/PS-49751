package ps.project.domain.production

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
)