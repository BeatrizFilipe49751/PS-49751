package ps.project.services

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import ps.project.domain.Cv
import ps.project.domain.CvDTO
import ps.project.domain.auth.User
import ps.project.repository.*

@Service
class CvService(
    private val cvRepository: CvRepository,
    private val userTokenRepository: UserTokenRepository,
    private val parser: CvParserService,
    private val exportService: CvExportService,
    private val persistenceService: CvPersistenceService
) {
    fun sendToCienciaVitae(token: String) {
        val user = getUserFromToken(token)
        val cv = user.cv ?: throw IllegalArgumentException("Sem CV válido.")
        val baseUrl = buildBaseUrl(user.cienciaID)
        exportService.sendCvToCienciaVitae(cv, baseUrl)
    }

    fun updateCv(token: String, cvDTO: CvDTO) {
    }

    @Transactional
    fun importCv(token: String, source: String, file: MultipartFile) : Cv {
        val user = getUserFromToken(token)
        val cvDTO = parser.parse(file, source)
        val savedCv = cvRepository.save(Cv(user = user, summary = cvDTO.summary))
        persistenceService.saveCvFromDto(cvDTO, savedCv) // save(cvDTO, savedCv)
        val id = savedCv.id
        return cvRepository.findById(savedCv.id)
            .orElseThrow { IllegalStateException("CV não encontrado com ID $id") }
    }

    private fun getUserFromToken(token: String): User {
        return userTokenRepository.findByToken(token)
            .orElseThrow { IllegalArgumentException("Token inválido ou expirado.") }
            .user
    }

    private fun buildBaseUrl(cienciaID: String): String {
        return "http://localhost:8080/api/v1.1/curriculum/$cienciaID/"
    }
}