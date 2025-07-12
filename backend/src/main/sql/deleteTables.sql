begin transaction;

-- ======================
-- AUTHORSHIP
-- ======================
DROP TABLE IF EXISTS Production_Author CASCADE;
DROP TABLE IF EXISTS Project_Author CASCADE;
DROP TABLE IF EXISTS Author CASCADE;

-- ======================
-- THESIS & SUPERVISORS
-- ======================
DROP TABLE IF EXISTS Supervisor CASCADE;
DROP TABLE IF EXISTS Thesis CASCADE;

-- ======================
-- PRODUCTIONS
-- ======================
DROP TABLE IF EXISTS WEBSITE_PROD CASCADE;
DROP TABLE IF EXISTS REPORT CASCADE;
DROP TABLE IF EXISTS SOFTWARE CASCADE;
DROP TABLE IF EXISTS OTHER CASCADE;
DROP TABLE IF EXISTS BOOK_CHAPTER CASCADE;
DROP TABLE IF EXISTS TRANSLATION CASCADE;
DROP TABLE IF EXISTS NEWSPAPER CASCADE;
DROP TABLE IF EXISTS MAGAZINE CASCADE;
DROP TABLE IF EXISTS JOURNAL CASCADE;
DROP TABLE IF EXISTS ARTICLE CASCADE;
DROP TABLE IF EXISTS RESEARCH_TECHNIQUE CASCADE;
DROP TABLE IF EXISTS PATENT CASCADE;
DROP TABLE IF EXISTS EDITED_BOOK CASCADE;
DROP TABLE IF EXISTS BOOK CASCADE;
DROP TABLE IF EXISTS Production CASCADE;

-- ======================
-- PROJECTS
-- ======================
DROP TABLE IF EXISTS Project CASCADE;
DROP TYPE IF EXISTS project_state CASCADE;
DROP TYPE IF EXISTS funding_type CASCADE;

-- ======================
-- ACTIVITIES
-- ======================
DROP TABLE IF EXISTS Activity_ProfessionalExperience CASCADE;
DROP TABLE IF EXISTS Consulting CASCADE;
DROP TABLE IF EXISTS Event CASCADE;
DROP TABLE IF EXISTS Academic_Jury CASCADE;
DROP TABLE IF EXISTS Subject_Taught CASCADE;
DROP TABLE IF EXISTS Supervision CASCADE;
DROP TABLE IF EXISTS Activity CASCADE;

-- ======================
-- PROFESSIONAL EXPERIENCE
-- ======================
DROP TABLE IF EXISTS Professional_Experience CASCADE;

-- ======================
-- DISTINCTIONS
-- ======================
DROP TABLE IF EXISTS Distinction CASCADE;
DROP TYPE IF EXISTS distinction_type CASCADE;

-- ======================
-- EDUCATION
-- ======================
DROP TABLE IF EXISTS Education CASCADE;
DROP TYPE IF EXISTS education_status CASCADE;
DROP TYPE IF EXISTS education_degree CASCADE;

-- ======================
-- IDENTIFIERS
-- ======================
DROP TABLE IF EXISTS Identifier CASCADE;
DROP TYPE IF EXISTS identifier_type CASCADE;

-- ======================
-- LANGUAGES
-- ======================
DROP TABLE IF EXISTS Language CASCADE;
DROP TYPE IF EXISTS language_level CASCADE;

-- ======================
-- CONTACT INFO
-- ======================
DROP TABLE IF EXISTS Email CASCADE;
DROP TABLE IF EXISTS Website CASCADE;
DROP TABLE IF EXISTS Phone CASCADE;
DROP TABLE IF EXISTS Address CASCADE;
DROP TYPE IF EXISTS website_type CASCADE;
DROP TYPE IF EXISTS phone_device CASCADE;
DROP TYPE IF EXISTS contact_type CASCADE;

-- ======================
-- USERS & AUTHENTICATION
-- ======================
DROP TABLE IF EXISTS UserToken CASCADE;
DROP TABLE IF EXISTS Users CASCADE;

-- ======================
-- SUPERVISOR ROLE ENUM
-- ======================
DROP TYPE IF EXISTS supervisor_role CASCADE;

end transaction;
