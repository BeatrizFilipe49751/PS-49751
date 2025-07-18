begin transaction;

-- ======================
-- USERS & AUTHENTICATION
-- ======================
CREATE TABLE Users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    ciencia_id VARCHAR(255) UNIQUE NOT NULL,
    summary TEXT
);

CREATE TABLE UserToken (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES Users(id) ON DELETE CASCADE,
    token VARCHAR(255) UNIQUE NOT NULL,
    issued_at TIMESTAMP NOT NULL,
    expires_at TIMESTAMP NOT NULL
);

-- ======================
-- CONTACT INFO
-- ======================
CREATE TABLE Address (
    id SERIAL PRIMARY KEY,
    type VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    locality VARCHAR(100) NOT NULL,
    municipality VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL,
    user_id INT REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE Phone (
    id SERIAL PRIMARY KEY,
    type VARCHAR(100) NOT NULL,
    number INT NOT NULL,
    country_code INT,
    device VARCHAR(100) NOT NULL,
    user_id INT REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE Website (
    id SERIAL PRIMARY KEY,
    type VARCHAR(100) NOT NULL,
    url TEXT NOT NULL,
    user_id INT REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE Email (
    id SERIAL PRIMARY KEY,
    type VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    user_id INT REFERENCES Users(id) ON DELETE CASCADE
);

-- ======================
-- LANGUAGES
-- ======================
CREATE TABLE Language (
    id SERIAL PRIMARY KEY,
    language VARCHAR(100) NOT NULL,
    comprehension VARCHAR(100),
    reading VARCHAR(100),
    speaking VARCHAR(100),
    writing VARCHAR(100),
    user_id INTEGER REFERENCES Users(id) ON DELETE CASCADE
);

-- ======================
-- IDENTIFIERS
-- ======================
CREATE TABLE Identifier (
    id VARCHAR(255) PRIMARY KEY,
    type VARCHAR(100) NOT NULL,
    user_id INTEGER REFERENCES Users(id) ON DELETE CASCADE
);

-- ======================
-- EDUCATION
-- ======================
-- Education Table
CREATE TABLE Education (
    id SERIAL PRIMARY KEY,
    degree VARCHAR(255) NOT NULL,
    course VARCHAR(255) NOT NULL,
    institution VARCHAR(255) NOT NULL,
    classification VARCHAR(50),
    status VARCHAR(255) NOT NULL,
    course_code VARCHAR(100),
    start_date DATE,
    end_date DATE NOT NULL,
    user_id INTEGER REFERENCES Users(id) ON DELETE CASCADE
);

-- ======================
-- DISTINCTIONS
-- ======================
-- Distinction Table
CREATE TABLE Distinction (
    id SERIAL PRIMARY KEY,
    type VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    year INT NOT NULL,
    promoting_entity VARCHAR(255),
    user_id INTEGER REFERENCES Users(id) ON DELETE CASCADE
);

-- ======================
-- PROFESSIONAL EXPERIENCE
-- ======================
CREATE TABLE Professional_Experience (
    id SERIAL PRIMARY KEY,
    institution VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE,
    user_id INTEGER REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE Science (
    id INTEGER PRIMARY KEY REFERENCES Professional_Experience(id) ON DELETE CASCADE
);

CREATE TABLE Teaching_HE (
    id INTEGER PRIMARY KEY REFERENCES Professional_Experience(id) ON DELETE CASCADE
);

CREATE TABLE Positions (
    id INTEGER PRIMARY KEY REFERENCES Professional_Experience(id) ON DELETE CASCADE
);

CREATE TABLE Others (
    id INTEGER PRIMARY KEY REFERENCES Professional_Experience(id) ON DELETE CASCADE
);

-- ======================
-- ACTIVITIES
-- ======================
CREATE TABLE Activity (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    user_id INTEGER REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE Supervision (
    id INTEGER PRIMARY KEY REFERENCES Activity(id) ON DELETE CASCADE,
    role VARCHAR(255) NOT NULL,
    supervisee VARCHAR(255) NOT NULL,
    institution VARCHAR(255),
    course VARCHAR(255),
    course_code VARCHAR(100),
    degree VARCHAR(50) not NULL
);

CREATE TABLE Subject_Taught (
    id INTEGER PRIMARY KEY REFERENCES Activity(id) ON DELETE CASCADE,
    course VARCHAR(255),
    course_code VARCHAR(100),
    end_date DATE
);

CREATE TABLE Academic_Jury (
    id INTEGER PRIMARY KEY REFERENCES Activity(id) ON DELETE CASCADE,
    candidate VARCHAR(255) NOT NULL,
    institution VARCHAR(255),
    course VARCHAR(255),
    degree VARCHAR(50) not NULL
);

CREATE TABLE Event (
    id INTEGER PRIMARY KEY REFERENCES Activity(id) ON DELETE CASCADE,
    institution VARCHAR(255)
);

CREATE TABLE Consulting (
    id INTEGER PRIMARY KEY REFERENCES Activity(id) ON DELETE CASCADE,
    end_date DATE
);

CREATE TABLE Activity_ProfessionalExperience (
    activity_id INT REFERENCES Activity(id) ON DELETE CASCADE,
    experience_id INT REFERENCES Professional_Experience(id) ON DELETE CASCADE,
    PRIMARY KEY (activity_id, experience_id)
);

-- ======================
-- PROJECTS
-- ======================
CREATE TABLE Project (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    institution VARCHAR(255),
    start_date DATE NOT NULL,
    end_date DATE,
    description TEXT,
    funding_type VARCHAR(255) NOT NULL,
    identifier VARCHAR(255),
    role VARCHAR(255),
    state VARCHAR(255),
    user_id INTEGER REFERENCES Users(id) ON DELETE CASCADE
);

-- ======================
-- PRODUCTIONS
-- ======================
CREATE TABLE Production (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    user_id INTEGER REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE BOOK (
    id INTEGER PRIMARY KEY REFERENCES Production(id) ON DELETE CASCADE,
    doi VARCHAR(100),
    isbn VARCHAR(50),
    volume VARCHAR(50),
    url VARCHAR(255)
);

CREATE TABLE EDITED_BOOK (
    id INTEGER PRIMARY KEY REFERENCES Production(id) ON DELETE CASCADE,
    doi VARCHAR(100),
    isbn VARCHAR(50),
    volume VARCHAR(50),
    url VARCHAR(255)
);

CREATE TABLE PATENT (
    id INTEGER PRIMARY KEY REFERENCES Production(id) ON DELETE CASCADE
);

CREATE TABLE RESEARCH_TECHNIQUE (
    id INTEGER PRIMARY KEY REFERENCES Production(id) ON DELETE CASCADE,
    doi VARCHAR(100)
);

CREATE TABLE ARTICLE (
    id INTEGER PRIMARY KEY REFERENCES Production(id) ON DELETE CASCADE,
    doi VARCHAR(100),
    pageFrom VARCHAR(50),
    pageTo VARCHAR(50),
    secondary_title VARCHAR(255),
    volume VARCHAR(50),
    url VARCHAR(255)
);

CREATE TABLE JOURNAL (
    id INTEGER PRIMARY KEY REFERENCES ARTICLE(id) ON DELETE CASCADE,
    issn VARCHAR(50)
);

CREATE TABLE MAGAZINE (
    id INTEGER PRIMARY KEY REFERENCES ARTICLE(id) ON DELETE CASCADE,
    issn VARCHAR(50)
);

CREATE TABLE NEWSPAPER (
    id INTEGER PRIMARY KEY REFERENCES ARTICLE(id) ON DELETE CASCADE,
    issn VARCHAR(50)
);

CREATE TABLE TRANSLATION (
    id INTEGER PRIMARY KEY REFERENCES ARTICLE(id) ON DELETE CASCADE
);

CREATE TABLE BOOK_CHAPTER (
    id INTEGER PRIMARY KEY REFERENCES ARTICLE(id) ON DELETE CASCADE,
    isbn VARCHAR(50)
);

CREATE TABLE OTHER (
    id INTEGER PRIMARY KEY REFERENCES Production(id) ON DELETE CASCADE,
    description TEXT,
    url VARCHAR(255)
);

CREATE TABLE SOFTWARE (
    id INTEGER PRIMARY KEY REFERENCES Production(id) ON DELETE CASCADE,
    description TEXT,
    version VARCHAR(50),
    platform VARCHAR(100),
    doi VARCHAR(100)
);

CREATE TABLE REPORT (
    id INTEGER PRIMARY KEY REFERENCES Production(id) ON DELETE CASCADE,
    url TEXT,
    volume VARCHAR(50),
    doi VARCHAR(100)
);

CREATE TABLE WEBSITE_PROD (
    id INTEGER PRIMARY KEY REFERENCES Production(id) ON DELETE CASCADE,
    url TEXT,
    description TEXT
);

-- ======================
-- THESIS & SUPERVISORS
-- ======================
CREATE TABLE Thesis (
    id INTEGER PRIMARY KEY REFERENCES Production(id) ON DELETE CASCADE,
    education_id INTEGER UNIQUE NOT NULL REFERENCES Education(id) ON DELETE CASCADE,
    name TEXT,
    description TEXT
);

CREATE TABLE Supervisor (
    thesis_id INTEGER NOT NULL REFERENCES Thesis(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    PRIMARY KEY (thesis_id, name)
);

-- ======================
-- AUTHORSHIP
-- ======================
CREATE TABLE Author (
    id SERIAL PRIMARY KEY,
    citation_name VARCHAR(255) UNIQUE NOT NULL,
    user_id INTEGER REFERENCES Users(id) ON DELETE CASCADE
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

end transaction;