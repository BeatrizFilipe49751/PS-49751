package ps.project.mapping.xml.cienciaVitae.production

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import ps.project.mapping.xml.cienciaVitae.common.CienciaVitaeXmlPayload
import ps.project.mapping.xml.cienciaVitae.common.CodeValueXml

@JacksonXmlRootElement(localName = "output", namespace = "http://www.cienciavitae.pt/ns/output")
data class OutputXml(
    @JacksonXmlProperty(localName = "outputCategory")
    val outputCategory: CodeValueXml,

    @JacksonXmlProperty(localName = "outputType")
    val outputType: CodeValueXml,

    @JacksonXmlProperty(localName = "journal-article")
    val journalArticle: JournalArticleXml? = null,

    @JacksonXmlProperty(localName = "book")
    val book: BookXml? = null,

    @JacksonXmlProperty(localName = "edited-book")
    val editedBook: EditedBookXml? = null,

    @JacksonXmlProperty(localName = "book-chapter")
    val bookChapter: BookChapterXml? = null,

    @JacksonXmlProperty(localName = "translation")
    val translation: TranslationXml? = null,

    @JacksonXmlProperty(localName = "newspaper-article")
    val newspaperArticle: NewspaperArticleXml? = null,

    @JacksonXmlProperty(localName = "magazine-article")
    val magazineArticle: MagazineArticleXml? = null,

    @JacksonXmlProperty(localName = "report")
    val report: ReportXml? = null,

    @JacksonXmlProperty(localName = "website")
    val website: WebsiteXml? = null,

    @JacksonXmlProperty(localName = "patent")
    val patent: PatentXml? = null,

    @JacksonXmlProperty(localName = "research-technique")
    val researchTechnique: ResearchTechniqueXml? = null,

    @JacksonXmlProperty(localName = "software")
    val software: SoftwareXml? = null,

    @JacksonXmlProperty(localName = "other-output")
    val otherOutput: OtherOutputXml? = null
) : CienciaVitaeXmlPayload()
