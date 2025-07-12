package ps.project.mapping.xml.lattes.distinction

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.domain.distinction.DistinctionDTO
import ps.project.domain.distinction.DistinctionType

data class LattesDistinctionXml(
    @JacksonXmlProperty(isAttribute = true, localName = "NOME-DO-PREMIO-OU-TITULO")
    val name: String,

    @JacksonXmlProperty(isAttribute = true, localName = "ANO-DA-PREMIACAO")
    val year: String,

    @JacksonXmlProperty(isAttribute = true, localName = "NOME-DA-ENTIDADE-PROMOTORA")
    val promoter: String?
) {
    fun toDTO() = DistinctionDTO(
        type = DistinctionType.Award,
        name = name,
        year = year.toIntOrNull() ?: 0,
        promotingEntity = promoter
    )
}
