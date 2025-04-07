package ps.project.domain

interface CvParser {
    fun supports(source: String): Boolean
    fun parse(xml: String): CvDTO
}