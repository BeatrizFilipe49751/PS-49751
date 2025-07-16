package ps.project.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ps.project.domain.CvDTO
import ps.project.domain.CvImportResponse
import ps.project.services.CvService

@RestController
@RequestMapping("/api/cv")
class CvController(private val cvService: CvService) {

    @PostMapping("/send")
    fun sendCvToCienciaVitae(
        @RequestHeader("Authorization") authHeader: String
    ): ResponseEntity<String> {
        return try {
            val token = authHeader.removePrefix("Bearer ")
            cvService.sendToCienciaVitae(token)
            ResponseEntity.ok("CV successfully sent to CiênciaVitae.")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
        }
    }

    @PutMapping("/update")
    fun updateCv(
        @RequestHeader("Authorization") authHeader: String,
        @RequestBody cvDTO: CvDTO
    ): ResponseEntity<String> {
        return try {
            val token = authHeader.removePrefix("Bearer ")
            cvService.updateCv(token, cvDTO)
            ResponseEntity.ok("CV successfully updated.")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
        }
    }

    @PostMapping("/import")
    fun importCv(
        @RequestHeader("Authorization") authHeader: String,
        @RequestParam source: String,
        @RequestParam file: MultipartFile
    ): ResponseEntity<Any> {
        return try {
            val token = authHeader.replace("Bearer ", "")
            val cv = cvService.importCv(token, source, file)
            val response = CvImportResponse (
                message = "CV successfully imported from source $source.",
                id = cv.id,
                summary = cv.summary,
                identifiers = cv.identifiers,
                languages = cv.languages,
                distinctions = cv.distinctions,
                addresses = cv.addresses,
                emails = cv.emails,
                phones = cv.phones,
                websites = cv.websites,
                educations = cv.educations,
                projects = cv.projects,
                productions = cv.productions,
                profExp = cv.profExp,
                activities = cv.activities,
                authors = cv.authors
            )
            ResponseEntity.ok(response)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
        }
    }

    @GetMapping
    fun getCv(
        @RequestHeader("Authorization") authHeader: String
    ): ResponseEntity<CvDTO> {
        return try {
            val token = authHeader.removePrefix("Bearer ")
            val cvDTO = cvService.getCvForUser(token)
            ResponseEntity.ok(cvDTO)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

}