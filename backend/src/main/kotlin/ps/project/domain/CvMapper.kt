package ps.project.domain

interface CvMapper {
    fun supports(source: String): Boolean
    fun extractCv(xmlString: String): CvDTO
}