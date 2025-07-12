package ps.project.domain.identifier

enum class IdentifierType(val value: String) {
    ORCID("ORCID iD"),
    WOS("Researcher Id"),
    SCOPUS("Scopus Author Id"),
    GOOGLE("Google Scholar ID"),
    AUTHENTICUSID("Authenticus Id"),
    LATTESID("Lattes Id")
}