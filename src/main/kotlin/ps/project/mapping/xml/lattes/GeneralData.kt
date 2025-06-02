package ps.project.mapping.xml.lattes

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.mapping.xml.lattes.address.LattesAddressModel
import ps.project.mapping.xml.lattes.distinction.LattesDistinctionWrapper
import ps.project.mapping.xml.lattes.education.LattesEducationWrapper
import ps.project.mapping.xml.lattes.language.LattesLanguagesWrapper
import ps.project.mapping.xml.lattes.project.ProfessionalActivities

data class GeneralData(
    @JacksonXmlProperty(isAttribute = true, localName = "NOME-EM-CITACOES-BIBLIOGRAFICAS")
    val authors: String? = null,

    @JacksonXmlProperty(localName = "IDIOMAS")
    val languages: LattesLanguagesWrapper? = null,

    @JacksonXmlProperty(localName = "PREMIOS-TITULOS")
    val distinctions: LattesDistinctionWrapper? = null,

    @JacksonXmlProperty(localName = "RESUMO-CV")
    val summary: LattesSummaryModel? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ENDERECO")
    val addresses: List<LattesAddressModel> = emptyList(),

    @JacksonXmlProperty(localName = "ATUACOES-PROFISSIONAIS")
    val professionalActivities: ProfessionalActivities? = null,

    @JacksonXmlProperty(localName = "FORMACAO-ACADEMICA-TITULACAO")
    val educations: LattesEducationWrapper? = null,
)