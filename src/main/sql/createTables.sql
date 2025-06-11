begin transaction;

-- User Table
CREATE TABLE Users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    cienciaID VARCHAR(255) UNIQUE NOT NULL
);

-- UserToken Table
create table UserToken (
	id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES Users(id) ON DELETE CASCADE,
    token VARCHAR(512) NOT NULL UNIQUE,
    issued_at TIMESTAMP NOT NULL,
    expires_at TIMESTAMP NOT NULL
);

-- CV Table
CREATE TABLE CV (
    id SERIAL PRIMARY KEY,
    user_id INT UNIQUE REFERENCES Users(id) ON DELETE CASCADE,
    summary TEXT
);

-- Identifier Type Enum
CREATE TYPE identifier_type AS ENUM (
    'ORCID',
    'WOS',
    'SCOPUS',
    'GOOGLE',
    'AUTHENTICUSID',
    'LATTESID'
);

-- Identifier Table
CREATE TABLE Identifier (
    id VARCHAR(255) PRIMARY KEY,
    cv_id INT REFERENCES CV(id) ON DELETE CASCADE,
    type identifier_type NOT NULL
);

CREATE TYPE education_degree AS ENUM (
    'PrimaryEducation',
    'SecondaryEducation',
    'Bachelor',
    'Postgraduate',
    'Master',
    'Doctorate',
    'PostDoctorate',
    'Other'
);

CREATE TYPE education_status AS ENUM ('Ongoing', 'Attended', 'Concluded');

-- Education Table
CREATE TABLE Education (
    id SERIAL PRIMARY KEY,
    cv_id INT REFERENCES CV(id) ON DELETE CASCADE,
    degree education_degree NOT NULL,
    course VARCHAR(255) NOT NULL,
    institution VARCHAR(255) NOT NULL,
    classification VARCHAR(50),
    status education_status NOT NULL,
    course_code VARCHAR(100),
    start_date DATE,
    end_date DATE NOT NULL
);

-- Thesis Table
CREATE TABLE Thesis (
    id SERIAL PRIMARY KEY,
    education_id INT REFERENCES Education(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL
);

CREATE TYPE supervisor_role AS ENUM ('Supervisor', 'CoSupervisor');

-- Supervisor Table
CREATE TABLE Supervisor (
    id SERIAL PRIMARY KEY,
    thesis_id INT REFERENCES Thesis(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    role supervisor_role NOT NULL
);

-- Professional Experience Table
CREATE TABLE ProfessionalExperience (
    id SERIAL PRIMARY KEY,
    cv_id INT REFERENCES CV(id) ON DELETE CASCADE,
    institution VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE
);

-- Activity Table
CREATE TABLE Activity (
    id SERIAL PRIMARY KEY,
    cv_id INT REFERENCES CV(id) ON DELETE CASCADE,
    type VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE
);

CREATE TABLE Activity_ProfessionalExperience (
    activity_id INT REFERENCES Activity(id) ON DELETE CASCADE,
    experience_id INT REFERENCES ProfessionalExperience(id) ON DELETE CASCADE,
    PRIMARY KEY (activity_id, experience_id)
);

CREATE TYPE production_type AS ENUM (
    'JOURNAL_ARTICLE',
    'BOOK',
    'EDITED_BOOK',
    'BOOK_CHAPTER',
    'TRANSLATION',
    'NEWSPAPER_ARTICLE',
    'MAGAZINE_ARTICLE',
    'REPORT',
    'WEBSITE',
    'PATENT',
    'RESEARCH_TECHNIQUE',
    'SOFTWARE',
    'OTHER_OUTPUT'
);

-- Production Table
CREATE TABLE Production (
    id SERIAL PRIMARY KEY,
    cv_id INT REFERENCES CV(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    type production_type NOT NULL,
    date DATE NOT NULL,
    description TEXT,
    doi VARCHAR(255),
    issn VARCHAR(255),
    isbn VARCHAR(255),
    url VARCHAR(255),
    secondary_title VARCHAR(255),
    volume VARCHAR(255),
    version VARCHAR(255),
    platform VARCHAR(255),
    page_from VARCHAR(255),
    page_to VARCHAR(255)
);

-- Funding Type Enum
CREATE TYPE funding_type AS ENUM ('Grant', 'Other', 'Contract');

Create Type project_state as Enum ('Concluded', 'Cancelled', 'Ongoing');

-- Project Table
CREATE TABLE Project (
    id SERIAL PRIMARY KEY,
    cv_id INT REFERENCES CV(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    institution VARCHAR(255),
    start_date DATE NOT NULL,
    end_date DATE,
    description TEXT,
    funding_type funding_type NOT NULL,
    identifier VARCHAR(255),
    role VARCHAR(255),
    state project_state
);

-- Author Table
CREATE TABLE Author (
    id SERIAL PRIMARY KEY,
    citation_name VARCHAR(255) UNIQUE NOT NULL,
    cv_id INT REFERENCES CV(id) ON DELETE CASCADE
);

CREATE TABLE Project_Author (
    project_id INT REFERENCES Project(id) ON DELETE CASCADE,
    author_id INT REFERENCES Author(id) ON DELETE CASCADE,
    PRIMARY KEY (project_id, author_id)
);

CREATE TABLE Production_Author (
    production_id INT REFERENCES Production(id) ON DELETE CASCADE,
    author_id INT REFERENCES Author(id) ON DELETE CASCADE,
    PRIMARY KEY (production_id, author_id)
);

-- Distinction Type Enum
CREATE TYPE distinction_type AS ENUM ('Award', 'Title', 'Other');

-- Distinction Table
CREATE TABLE Distinction (
    id SERIAL PRIMARY KEY,
    cv_id INT REFERENCES CV(id) ON DELETE CASCADE,
    type distinction_type NOT NULL,
    name VARCHAR(255) NOT NULL,
    year INT NOT NULL,
    promoting_entity VARCHAR(255)
);

-- Personal/Professional Enum
CREATE TYPE contact_type AS ENUM ('Personal', 'Professional');

-- Address Table
CREATE TABLE Address (
    id SERIAL PRIMARY KEY,
    cv_id INT REFERENCES CV(id) ON DELETE CASCADE,
    type contact_type NOT NULL,
    address VARCHAR(255) NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    locality VARCHAR(100) NOT NULL,
    municipality VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL
);

-- Phone Device Enum
CREATE TYPE phone_device AS ENUM ('Telephone', 'Mobile', 'Fax', 'Pager');

-- Phone Table
CREATE TABLE Phone (
    id SERIAL PRIMARY KEY,
    cv_id INT REFERENCES CV(id) ON DELETE CASCADE,
    type contact_type NOT NULL,
    number INT NOT NULL,
    country_code INT,
    device phone_device NOT NULL
);

-- Email Table
CREATE TABLE Email (
    id SERIAL PRIMARY KEY,
    cv_id INT REFERENCES CV(id) ON DELETE CASCADE,
    type contact_type NOT NULL,
    address VARCHAR(255) NOT NULL
);

-- Website Type Enum
CREATE TYPE website_type AS ENUM ('Blog', 'Feed', 'Personal', 'SocialMedia', 'Professional', 'Scholar');

-- Website Table
CREATE TABLE Website (
    id SERIAL PRIMARY KEY,
    cv_id INT REFERENCES CV(id) ON DELETE CASCADE,
    type website_type NOT NULL,
    url TEXT NOT NULL
);

CREATE TYPE language_level AS ENUM ('Beginner', 'Elementary', 'Intermediate', 'UpperIntermediate', 'Advanced', 'Proficiency');

CREATE TABLE Language (
    id SERIAL PRIMARY KEY,
    cv_id INT REFERENCES CV(id) ON DELETE CASCADE,
    language VARCHAR(100) NOT NULL,
    comprehension language_level,
    reading language_level,
    speaking language_level,
    writing language_level
);

end transaction;