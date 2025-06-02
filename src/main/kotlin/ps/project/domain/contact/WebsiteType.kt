package ps.project.domain.contact

enum class WebsiteType(val code: Int, private val customValue: String? = null) {
    Blog(1),
    Feed(2),
    Personal(3),
    SocialMedia(4, "Social media"),
    Professional(5),
    Scholar(6);

    val value: String
        get() = customValue ?: name
}