CREATE SCHEMA IF NOT EXISTS sigha;
SET search_path TO sigha;

-- Tabla roles
CREATE TABLE IF NOT EXISTS roles (
                                     id SERIAL PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL UNIQUE
    );

-- Tabla type_document
CREATE TABLE IF NOT EXISTS type_document (
                                             id SERIAL PRIMARY KEY,
                                             description VARCHAR(255) NOT NULL UNIQUE,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Tabla semester
CREATE TABLE IF NOT EXISTS semester (
                                        id SERIAL PRIMARY KEY,
                                        description VARCHAR(255) NOT NULL UNIQUE,
    start_date DATE NOT NULL UNIQUE,
    end_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Tabla user
CREATE TABLE IF NOT EXISTS "user" (
                                      id SERIAL PRIMARY KEY,
                                      email VARCHAR(255) UNIQUE,
    id_type_document INTEGER NOT NULL,
    documento VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL,
    role_id INTEGER NOT NULL,
    token_hash VARCHAR(255),
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_type_document FOREIGN KEY (id_type_document) REFERENCES type_document(id),
    CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES roles(id)
    );

-- Tabla availability
CREATE TABLE IF NOT EXISTS availability (
                                            id SERIAL PRIMARY KEY,
                                            id_user INTEGER NOT NULL,
                                            id_semester INTEGER NOT NULL,
                                            start_time TIME NOT NULL,
                                            day_of_week VARCHAR(20) NOT NULL,
    CONSTRAINT fk_availability_user FOREIGN KEY (id_user) REFERENCES "user"(id),
    CONSTRAINT fk_availability_semester FOREIGN KEY (id_semester) REFERENCES semester(id)
    );

-- Datos base
INSERT INTO roles (name) VALUES
                             ('DIRECTOR DE ESCUELA'),
                             ('COORDINADOR ACADEMICO'),
                             ('PROFESOR')
    ON CONFLICT DO NOTHING;

INSERT INTO type_document (description) VALUES
    ('CEDULA DE CIUDADANIA')
    ON CONFLICT DO NOTHING;

INSERT INTO semester (description, start_date, end_date, created_at, updated_at)
VALUES ('2025-1', '2025-01-01', '2025-06-30', NOW(), NOW())
    ON CONFLICT DO NOTHING;
