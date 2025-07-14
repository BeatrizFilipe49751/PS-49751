package ps.project.mapping.mappers.lattes

import org.springframework.stereotype.Component
import ps.project.domain.profExp.ProfExpType
import ps.project.domain.profExp.ProfessionalExperienceDTO
import ps.project.mapping.xml.lattes.LattesCVModel
import ps.project.mapping.xml.lattes.ProfessionalActivity
import java.time.LocalDate

@Component
class LattesProfExpMapper {
    fun extractProfExperiences(cv: LattesCVModel): List<ProfessionalExperienceDTO> {
        return cv.generalData?.professionalActivities?.activities?.mapNotNull { mapToProfExpDTO(it) } ?: emptyList()
    }

    private fun mapToProfExpDTO(profActivity: ProfessionalActivity): ProfessionalExperienceDTO? {
        val startDates = profActivity.vinculos.mapNotNull { computeDate(it.startYear, it.startMonth) }
        val endDates = profActivity.vinculos.mapNotNull { computeDate(it.endYear, it.endMonth) }

        val earliestStart = startDates.minOrNull()!!
        val latestEnd = endDates.maxOrNull()

        val activityMapper = LattesActivityMapper()
        val activities = activityMapper.extractActivitiesFromProfExp(profActivity)

        return ProfessionalExperienceDTO(
            institution = profActivity.institution,
            description = profActivity.vinculos.first().description,
            startDate = earliestStart,
            endDate = latestEnd,
            type = ProfExpType.Others,
            activities = activities
        )
    }

    fun computeDate(year: String?, month: String?): LocalDate? {
        return if (!year.isNullOrBlank()) {
            LocalDate.of(year.toInt(), month?.toIntOrNull() ?: 1, 1)
        } else null
    }
}