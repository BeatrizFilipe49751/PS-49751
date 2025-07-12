package ps.project.mapping.xml.lattes.project

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.domain.project.FundingType

data class ProjectFunding(
    @JacksonXmlProperty(isAttribute = true, localName = "NATUREZA")
    val nature: String? = null
) {
    fun mapFundingType(): FundingType {
        return when (nature?.uppercase()) {
            "BOLSA" -> FundingType.Grant
            else -> FundingType.Other
        }
    }
}