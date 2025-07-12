package ps.project.mapping.xml.lattes.education

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import ps.project.domain.education.EducationDegree

data class LattesEducationWrapper(
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ENSINO-FUNDAMENTAL-PRIMEIRO-GRAU")
    val elementary: List<LattesEducation>? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ENSINO-MEDIO-SEGUNDO-GRAU")
    val highSchool: List<LattesEducation>? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "GRADUACAO")
    val graduation: List<LattesEducation>? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ESPECIALIZACAO")
    val specialization: List<LattesEducation>? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "MESTRADO")
    val master: List<LattesEducation>? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "DOUTORADO")
    val doctorate: List<LattesEducation>? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "POS-DOUTORADO")
    val postdoc: List<LattesEducation>? = null
) {
    fun getAllEntries(): List<Pair<List<LattesEducation>, EducationDegree>> {
        return listOfNotNull(
            elementary?.let { it to EducationDegree.fromInt(1) },
            highSchool?.let { it to EducationDegree.fromInt(2) },
            graduation?.let { it to EducationDegree.fromInt(3) },
            specialization?.let { it to EducationDegree.fromInt(4) },
            master?.let { it to EducationDegree.fromInt(5) },
            doctorate?.let { it to EducationDegree.fromInt(6) },
            postdoc?.let { it to EducationDegree.fromInt(7) }
        )
    }
}
