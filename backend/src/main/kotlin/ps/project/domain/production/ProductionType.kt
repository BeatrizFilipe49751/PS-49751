package ps.project.domain.production

enum class ProductionType(val code: String, private val customValue: String? = null) {
    JOURNAL_ARTICLE("P101"),
    BOOK("P103"),
    EDITED_BOOK("P104"),
    BOOK_CHAPTER("P105"),
    TRANSLATION("P107"),
    NEWSPAPER_ARTICLE("P110"),
    MAGAZINE_ARTICLE("P113"),
    REPORT("P115"),
    WEBSITE("P121"),
    PATENT("P401"),
    RESEARCH_TECHNIQUE("P505"),
    SOFTWARE("P512"),
    THESIS("P108", "Thesis / Dissertation"),
    OTHER_OUTPUT("P508");

    val value: String
        get() = customValue ?: name.lowercase()
            .replace('_', ' ')
            .replace("-", " / ")
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

    val categoryCode: String
        get() = code.take(2)

    val categoryName: String
        get() = categoryMap[categoryCode] ?: "Others"

    companion object {
        private val categoryMap = mapOf(
            "P1" to "Publications",
            "P3" to "Artistic/Performance",
            "P4" to "Intellectual Property",
            "P5" to "Others"
        )
    }
}
