package ps.project.domain.activity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import ps.project.domain.User
import java.time.LocalDate

@Entity
@Table(name = "Subject_Taught")
class SubjectTaught(
    id: Int,
    user: User,
    title: String,
    date: LocalDate,

    val course: String? = null,
    @Column(name = "course_code")
    val courseCode: String? = null,
    @Column(name = "end_date")
    val endDate: LocalDate? = null,
) : Activity(id = id, user = user, title = title, date = date)