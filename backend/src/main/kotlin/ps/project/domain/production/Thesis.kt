package ps.project.domain.production

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import ps.project.domain.User
import ps.project.domain.education.Education
import ps.project.domain.education.Supervisor
import java.time.LocalDate

@Entity
@Table(name = "THESIS")
class Thesis(
    id: Int,
    title: String,
    date: LocalDate,
    user: User,

    @OneToOne
    @JoinColumn(name = "education_id", unique = true, nullable = false)
    @JsonBackReference
    val education: Education
) : Production(id = id, title = title, date = date, user = user) {

    @OneToMany(mappedBy = "thesis")
    @JsonManagedReference
    val supervisors: MutableList<Supervisor> = mutableListOf()
}