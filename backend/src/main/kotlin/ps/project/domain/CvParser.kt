package ps.project.domain

import org.springframework.web.multipart.MultipartFile

interface CvParser {
    fun supports(source: String): Boolean
    fun parse(file: MultipartFile, source: String): CvDTO
}