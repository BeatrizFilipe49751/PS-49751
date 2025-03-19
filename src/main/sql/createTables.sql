begin transaction;

-- Gender Enum
CREATE TYPE gender_type AS ENUM (
    'Masculino',
    'Feminino'
);

-- User Table
CREATE TABLE Users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    birthdate DATE NOT NULL,
    gender gender_type NOT NULL,
    cienciaID VARCHAR(255) UNIQUE
);

-- Identifier Type Enum
CREATE TYPE identifier_type AS ENUM (
    'ORCID iD',
    'Researcher Id',
    'Scopus Author Id',
    'Google Scholar ID',
    'AuthenticusID',
    'LattesiD'
);

-- Identifier Table
CREATE TABLE Identifier (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES Users(id) ON DELETE CASCADE,
    type identifier_type NOT NULL
);

-- CV Table
CREATE TABLE CV (
    id SERIAL PRIMARY KEY,
    user_id INT UNIQUE REFERENCES Users(id) ON DELETE CASCADE,
    summary TEXT
);

CREATE TYPE education_status AS ENUM ('Em curso', 'Frequentou', 'Concluído');

-- Education Table
CREATE TABLE Education (
    id SERIAL PRIMARY KEY,
    cv_id INT REFERENCES CV(id) ON DELETE CASCADE,
    degree VARCHAR(255) NOT NULL,
    course VARCHAR(255) NOT NULL,
    institution VARCHAR(255) NOT NULL,
    classification VARCHAR(50),
    status education_status,
    courseCode VARCHAR(100),
    startDate DATE NOT NULL,
    endDate DATE
);

-- Thesis Table
CREATE TABLE Thesis (
    id SERIAL PRIMARY KEY,
    cv_id INT REFERENCES CV(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL
);

CREATE TYPE supervisor_role AS ENUM ('Orientador', 'Coorientador');

-- Supervisor Table
CREATE TABLE Supervisor (
    id SERIAL PRIMARY KEY,
    thesis_id INT REFERENCES Thesis(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    role supervisor_role
);

-- Professional Experience Table
CREATE TABLE ProfessionalExperience (
    id SERIAL PRIMARY KEY,
    cv_id INT REFERENCES CV(id) ON DELETE CASCADE,
    institution VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    description TEXT,
    startDate DATE NOT NULL,
    endDate DATE
);

-- Activity Table
CREATE TABLE Activity (
    id SERIAL PRIMARY KEY,
    cv_id INT REFERENCES CV(id) ON DELETE CASCADE,
    type VARCHAR(255) NOT NULL,
    description TEXT,
    startDate DATE NOT NULL,
    endDate DATE
);

CREATE TABLE Activity_ProfessionalExperience (
    activity_id INT REFERENCES Activity(id) ON DELETE CASCADE,
    experience_id INT REFERENCES ProfessionalExperience(id) ON DELETE CASCADE,
    PRIMARY KEY (activity_id, experience_id)
);

-- Production Table
CREATE TABLE Production (
    id SERIAL PRIMARY KEY,
    cv_id INT REFERENCES CV(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    description TEXT
);

-- Funding Type Enum
CREATE TYPE funding_type AS ENUM ('Bolsa', 'Outro', 'Projeto');

-- Project Table
CREATE TABLE Project (
    id SERIAL PRIMARY KEY,
    cv_id INT REFERENCES CV(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    institution VARCHAR(255),
    startDate DATE NOT NULL,
    endDate DATE,
    description TEXT,
    fundingType funding_type NOT NULL,
    identifier VARCHAR(255),
    role VARCHAR(255)
);

CREATE TABLE Project_Production (
    project_id INT REFERENCES Project(id) ON DELETE CASCADE,
    production_id INT REFERENCES Production(id) ON DELETE CASCADE,
    PRIMARY KEY (project_id, production_id)
);

-- Author Table
CREATE TABLE Author (
    id SERIAL PRIMARY KEY,
    citationName VARCHAR(255) NOT NULL,
    user_id INT REFERENCES Users(id) ON DELETE CASCADE
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
CREATE TYPE distinction_type AS ENUM ('Prémio', 'Título');

-- Distinction Table
CREATE TABLE Distinction (
    id SERIAL PRIMARY KEY,
    cv_id INT REFERENCES CV(id) ON DELETE CASCADE,
    type distinction_type NOT NULL,
    name VARCHAR(255) NOT NULL,
    year INT NOT NULL,
    promotingEntity VARCHAR(255) NOT NULL
);

-- Contact Table
CREATE TABLE Contact (
    id SERIAL PRIMARY KEY,
    cv_id INT REFERENCES CV(id) ON DELETE CASCADE
);

-- Personal/Professional Enum
CREATE TYPE contact_type AS ENUM ('Pessoal', 'Profissional');

-- Address Table
CREATE TABLE Address (
    id SERIAL PRIMARY KEY,
    contact_id INT REFERENCES Contact(id) ON DELETE CASCADE,
    type contact_type NOT NULL,
    address VARCHAR(255) NOT NULL,
    zipCode VARCHAR(20),
    locality VARCHAR(100),
    municipality VARCHAR(100),
    country VARCHAR(100)
);

-- Phone Device Enum
CREATE TYPE phone_device AS ENUM ('Fax', 'Pager', 'Telefone', 'Telemóvel');

-- Phone Table
CREATE TABLE Phone (
    id SERIAL PRIMARY KEY,
    contact_id INT REFERENCES Contact(id) ON DELETE CASCADE,
    type contact_type NOT NULL,
    number VARCHAR(20) NOT NULL,
    countryCode VARCHAR(10),
    device phone_device NOT NULL
);

-- Email Table
CREATE TABLE Email (
    id SERIAL PRIMARY KEY,
    contact_id INT REFERENCES Contact(id) ON DELETE CASCADE,
    type contact_type NOT NULL,
    address VARCHAR(255) NOT NULL
);

-- Website Type Enum
CREATE TYPE website_type AS ENUM ('Académico', 'Blogue', 'Feed', 'Pessoal', 'Rede social', 'Profissional');

-- Website Table
CREATE TABLE Website (
    id SERIAL PRIMARY KEY,
    contact_id INT REFERENCES Contact(id) ON DELETE CASCADE,
    type website_type NOT NULL,
    url TEXT NOT NULL
);

end transaction;