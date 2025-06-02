package ps.project.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import ps.project.domain.activity.Activity
import ps.project.domain.auth.User
import ps.project.domain.author.Author
import ps.project.domain.contact.Address
import ps.project.domain.contact.Email
import ps.project.domain.contact.Phone
import ps.project.domain.contact.Website
import ps.project.domain.distinction.Distinction
import ps.project.domain.education.Education
import ps.project.domain.identifier.Identifier
import ps.project.domain.language.Language
import ps.project.domain.production.Production
import ps.project.domain.profExp.ProfessionalExperience
import ps.project.domain.project.Project

@Entity
@Table(name = "CV")
data class Cv (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    @JsonBackReference
    val user: User,

    @Column(columnDefinition = "TEXT")
    val summary: String?,

    @OneToMany(mappedBy = "cv", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val languages: List<Language> = emptyList(),

    @OneToMany(mappedBy = "cv", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val distinctions: List<Distinction> = emptyList(),

    @OneToMany(mappedBy = "cv", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val addresses: List<Address> = emptyList(),

    @OneToMany(mappedBy = "cv", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val emails: List<Email> = emptyList(),

    @OneToMany(mappedBy = "cv", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val phones: List<Phone> = emptyList(),

    @OneToMany(mappedBy = "cv", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val websites: List<Website> = emptyList(),

    @OneToMany(mappedBy = "cv", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val educations: List<Education> = emptyList(),

    @OneToMany(mappedBy = "cv", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val projects: List<Project> = emptyList(),

    @OneToMany(mappedBy = "cv", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val identifiers: List<Identifier> = emptyList(),

    @OneToMany(mappedBy = "cv", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val productions: List<Production> = emptyList(),

    @OneToMany(mappedBy = "cv", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val profExp: List<ProfessionalExperience> = emptyList(),

    @OneToMany(mappedBy = "cv", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val activities: List<Activity> = emptyList(),

    @OneToMany(mappedBy = "cv", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val authors: List<Author> = emptyList()
)