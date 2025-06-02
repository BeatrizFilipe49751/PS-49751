package ps.project.services

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ps.project.domain.CvDTO
import ps.project.domain.CvMapper
import ps.project.domain.CvParser
import javax.xml.parsers.DocumentBuilderFactory
import java.io.StringReader
import org.xml.sax.InputSource
import java.io.File
import java.io.InputStreamReader

@Service
class CvParserService(private val mappers: List<CvMapper>) : CvParser {
    override fun supports(source: String): Boolean {
        return mappers.any { it.supports(source) }
    }

    override fun parse(file: MultipartFile, source: String): CvDTO {
        val input = InputStreamReader(file.inputStream, Charsets.ISO_8859_1)
        val content = input.readText()

        val mapper = mappers.find { it.supports(source) }
            ?: throw IllegalArgumentException("No mapper found for source: $source")

        return mapper.extractCv(content)
    }
}
