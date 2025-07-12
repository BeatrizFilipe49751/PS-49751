package ps.project.domain.activity

import ps.project.domain.User
import ps.project.domain.education.SupervisorRole

object ActivityMapper {
    /* ------------  DTO ➜ Entity  ------------ */
    fun toEntity(dto: ActivityDTO, user: User): Activity? {
        return when (dto.type) {
            ActivityType.AcademicJury -> AcademicJury(
                user = user,
                title = dto.title,
                date = dto.date,
                candidate = dto.student!!,
                institution = dto.institution,
                course = dto.course,
                degree = dto.degree!!.name
            )
            ActivityType.Supervision -> Supervision(
                user = user,
                title = dto.title,
                date = dto.date,
                role = dto.role!!.name,
                supervisee = dto.student!!,
                institution = dto.institution,
                course = dto.course,
                courseCode = dto.courseCode,
                degree = dto.degree!!.name
            )
            ActivityType.SubjectTaught -> SubjectTaught(
                user = user,
                title = dto.title,
                date = dto.date,
                course = dto.course,
                courseCode = dto.courseCode,
                endDate = dto.endDate,
            )
            ActivityType.Consulting -> Consulting(
                user = user,
                title = dto.title,
                date = dto.date,
                institution = dto.institution,
            )
            ActivityType.Event -> Event(
                user = user,
                title = dto.title,
                date = dto.date,
                endDate = dto.endDate
            )
        }
    }

    /* ------------  Entity ➜ DTO  ------------ */
    fun toDTO(activity: Activity): ActivityDTO? {
        return when (activity) {
            is AcademicJury -> ActivityDTO(
                id = activity.id,
                type = ActivityType.AcademicJury,
                title = activity.title,
                date = activity.date,
                student = activity.candidate,
                institution = activity.institution,
                course = activity.course,
                degree = DegreeType.valueOf(activity.degree),
            )
            is Supervision -> ActivityDTO(
                id = activity.id,
                type = ActivityType.Supervision,
                title = activity.title,
                date = activity.date,
                student = activity.supervisee,
                institution = activity.institution,
                course = activity.course,
                courseCode = activity.courseCode,
                role = SupervisorRole.valueOf(activity.role),
                degree = DegreeType.valueOf(activity.degree)
            )
            is SubjectTaught -> ActivityDTO(
                id = activity.id,
                type = ActivityType.SubjectTaught,
                title = activity.title,
                date = activity.date,
                course = activity.course,
                courseCode = activity.courseCode,
                endDate = activity.endDate
            )
            is Consulting -> ActivityDTO(
                id = activity.id,
                type = ActivityType.Consulting,
                title = activity.title,
                date = activity.date,
                institution = activity.institution
            )
            is Event -> ActivityDTO(
                id = activity.id,
                type = ActivityType.Event,
                title = activity.title,
                date = activity.date,
                endDate = activity.endDate
            )
            else -> null
        }
    }
}