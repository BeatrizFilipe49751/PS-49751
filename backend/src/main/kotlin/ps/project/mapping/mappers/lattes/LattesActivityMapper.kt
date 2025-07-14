package ps.project.mapping.mappers.lattes

import org.springframework.stereotype.Component
import ps.project.domain.activity.ActivityDTO
import ps.project.domain.activity.ActivityType
import ps.project.domain.activity.DegreeType
import ps.project.domain.education.SupervisorRole
import ps.project.mapping.xml.lattes.LattesCVModel
import ps.project.mapping.xml.lattes.ProfessionalActivity
import ps.project.mapping.xml.lattes.activities.LattesConsultingXml
import ps.project.mapping.xml.lattes.activities.LattesEventXml
import ps.project.mapping.xml.lattes.activities.LattesJuryParticipation
import ps.project.mapping.xml.lattes.activities.LattesJuryParticipationWrapper
import ps.project.mapping.xml.lattes.activities.LattesTeachingXml
import ps.project.mapping.xml.lattes.activities.supervision.LattesSupervisionBasicDataXml
import ps.project.mapping.xml.lattes.activities.supervision.LattesSupervisionDetailsXml
import ps.project.mapping.xml.lattes.activities.supervision.LattesSupervisionsXml
import java.time.LocalDate

@Component
class LattesActivityMapper {
    fun extractActivities(cv: LattesCVModel): List<ActivityDTO> = sequence {
        cv.complementaryData?.complementaryEducation?.juryParticipation?.let { juryParticipation ->
            yieldAll(extractJuryParticipationActivities(juryParticipation))
        }
        cv.technicalProduction?.other?.events?.let { events ->
            yieldAll(extractEventActivities(events))
        }
        cv.otherProduction?.supervisions?.let { supervisions ->
            yieldAll(extractSupervisionActivities(supervisions))
        }
    }.toList()

    fun extractActivitiesFromProfExp(profActivity: ProfessionalActivity): List<ActivityDTO> {
        val activities = mutableListOf<ActivityDTO>()
        val consultingActivities = profActivity.consultingActivities?.consultingActivities
        if (consultingActivities != null) {
            extractConsulting(consultingActivities).let { activities.addAll(it) }
        }
        val teachingActivities = profActivity.teachingActivities?.teachingActivities
        if (teachingActivities != null) {
            extractTeachingActivities(teachingActivities).let { activities.addAll(it) }
        }
        return activities
    }

    private fun extractConsulting(consultingActivities: List<LattesConsultingXml>): List<ActivityDTO> {
        val activities = mutableListOf<ActivityDTO>()
        consultingActivities.forEach { consulting ->
            val endDate = consulting.endYear?.let { toDate(year = it, month = consulting.endMonth) }
            activities.add(
                ActivityDTO(
                    title = consulting.title,
                    date = toDate(year = consulting.startYear, month = consulting.startMonth),
                    endDate = endDate,
                    type = ActivityType.Consulting
                )
            )
        }
        return activities
    }

    private fun extractTeachingActivities(teachingActivities: List<LattesTeachingXml>): List<ActivityDTO> {
        val activities = mutableListOf< ActivityDTO>()
        teachingActivities.forEach { teaching ->
            teaching.subjects.forEach { subject ->
                val endDate = teaching.endYear?.let { toDate(year = it, month = teaching.endMonth) }
                activities.add(
                    ActivityDTO(
                        title = subject.name,
                        date = toDate(year = teaching.startYear, month = teaching.startMonth),
                        endDate = endDate,
                        type = ActivityType.SubjectTaught,
                        course = teaching.degree,
                        courseCode = teaching.degreeCode
                    )
                )
            }
        }
        return activities
    }

    private fun extractJuryParticipationActivities(juryParticipations: LattesJuryParticipationWrapper): List<ActivityDTO> {
        fun mapEntry(list: List<LattesJuryParticipation>?, degree: DegreeType) =
            list?.map { jp ->
                val data = jp.basicData
                val details = jp.details
                ActivityDTO(
                    title        = data.title,
                    date         = toDate(year = data.year, month = null),
                    student      = details.candidate,
                    course       = details.course,
                    institution  = details.institution,
                    type         = ActivityType.AcademicJury,
                    degree       = degree
                )
            }.orEmpty()

        return buildList {
            addAll(mapEntry(juryParticipations.masters, DegreeType.MASTERS))
            addAll(mapEntry(juryParticipations.doctorate,DegreeType.DOCTORATE))
            addAll(mapEntry(juryParticipations.other,DegreeType.OTHER))
        }
    }

    private fun extractEventActivities(events: List<LattesEventXml>): List<ActivityDTO> {
        val activities = mutableListOf< ActivityDTO>()
        events.forEach { event ->
            val data = event.basicData
            activities.add(
                ActivityDTO(
                    title = data.title,
                    date = toDate(year = data.year, null),
                    type = ActivityType.Event,
                    institution = event.details?.institution,
                )
            )
        }
        return activities
    }

    private fun extractSupervisionActivities(supervisions: LattesSupervisionsXml): List<ActivityDTO> {
        val masters = supervisions.masterSupervisions
        val phd = supervisions.phdSupervisions
        val postdoc = supervisions.postdocSupervisions
        val other = supervisions.otherSupervisions
        return buildList {
            masters?.forEach { supervision ->
                add(mapSupervisionEntry(supervision.basicData,
                    supervision.details, DegreeType.MASTERS))
            }
            phd?.forEach { supervision ->
                add(mapSupervisionEntry(supervision.basicData,
                    supervision.details, DegreeType.DOCTORATE))
            }
            postdoc?.forEach { supervision ->
                add(mapSupervisionEntry(supervision.basicData,
                    supervision.details, DegreeType.POSTDOC))
            }
            other?.forEach { supervision ->
                val data = supervision.basicData
                val degree = when (data.type) {
                    "MONOGRAFIA_DE_CONCLUSAO_DE_CURSO_APERFEICOAMENTO_E_ESPECIALIZACAO" -> DegreeType.SPECIALIZATION
                    "TRABALHO_DE_CONCLUSAO_DE_CURSO_GRADUACAO" -> DegreeType.BACHELOR
                    "INICIACAO_CIENTIFICA" -> DegreeType.RESEARCH
                    else -> DegreeType.OTHER
                }
                add(mapSupervisionEntry(null,supervision.details, degree,data.title,data.year))
            }
        }
    }

    private fun mapSupervisionEntry(
        supervision: LattesSupervisionBasicDataXml?,
        details: LattesSupervisionDetailsXml,
        degree: DegreeType,
        title: String? = null,
        year: Int? = null): ActivityDTO {
            val startDate = toDate(year = (supervision?.year ?: year)!!, month = null)
            return ActivityDTO(
                title = (supervision?.title ?: title)!!,
                date = startDate,
                student = details.supervisee,
                role = if (details.role == "ORIENTADOR_PRINCIPAL") SupervisorRole.Supervisor else SupervisorRole.CoSupervisor,
                institution = details.institution,
                type = ActivityType.Supervision,
                degree = degree,
                course = details.degree,
                courseCode = details.degreeCode
            )
        }

    private fun toDate(year: Int, month: Int?): LocalDate {
        return year.let { LocalDate.of(it, month ?: 1, 1) }
    }
}