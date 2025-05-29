CREATE SCHEMA IF NOT EXISTS sigha;
SET search_path TO sigha;

-- Tabla sigla
CREATE TABLE IF NOT EXISTS sigla (
                                     id SERIAL PRIMARY KEY,
                                     sigla VARCHAR(255),
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Tabla roles
CREATE TABLE IF NOT EXISTS roles (
                                     id SERIAL PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL UNIQUE,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Tabla type_document
CREATE TABLE IF NOT EXISTS type_document (
                                             id SERIAL PRIMARY KEY,
                                             sigla_id INTEGER REFERENCES sigla(id),
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
    id_type_document INTEGER NOT NULL REFERENCES type_document(id),
    documento VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL,
    token_hash VARCHAR(255),
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Tabla user_rol
CREATE TABLE IF NOT EXISTS user_rol (
                                        id SERIAL PRIMARY KEY,
                                        id_user INTEGER NOT NULL REFERENCES "user"(id),
    id_role INTEGER NOT NULL REFERENCES roles(id),
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Tabla area
CREATE TABLE IF NOT EXISTS area (
                                    id SERIAL PRIMARY KEY,
                                    description VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Tabla user_area
CREATE TABLE IF NOT EXISTS user_area (
                                         id SERIAL PRIMARY KEY,
                                         id_user INTEGER NOT NULL REFERENCES "user"(id),
    id_area INTEGER NOT NULL REFERENCES area(id),
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Tabla level_subject
CREATE TABLE IF NOT EXISTS level_subject (
                                             id SERIAL PRIMARY KEY,
                                             description VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Tabla subject
CREATE TABLE IF NOT EXISTS subject (
                                       id SERIAL PRIMARY KEY,
                                       id_area INTEGER NOT NULL REFERENCES area(id),
    id_level_subject INTEGER NOT NULL REFERENCES level_subject(id),
    codigo VARCHAR(255),
    name VARCHAR(255),
    max_students INTEGER,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Tabla status_availability
CREATE TABLE IF NOT EXISTS status_availability (
                                                   id SERIAL PRIMARY KEY,
                                                   description VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Tabla availability
CREATE TABLE IF NOT EXISTS availability (
                                            id SERIAL PRIMARY KEY,
                                            id_user INTEGER NOT NULL REFERENCES "user"(id),
    id_semester INTEGER NOT NULL REFERENCES semester(id),
    id_status_availability INTEGER NOT NULL REFERENCES status_availability(id),
    start_time TIME NOT NULL,
    day_of_week VARCHAR(20) NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Datos base
INSERT INTO roles (name) VALUES
                             ('DIRECTOR DE ESCUELA'),
                             ('COORDINADOR ACADEMICO'),
                             ('PROFESOR')
    ON CONFLICT DO NOTHING;
insert into sigla (sigla )values
    ('C.C')
    on conflict do nothing;

INSERT INTO type_document (sigla_id,description) VALUES
    ('1','CEDULA DE CIUDADANIA')
    ON CONFLICT DO NOTHING;

INSERT INTO semester (description, start_date, end_date, created_at, updated_at)
VALUES ('2025-1', '2025-01-01', '2025-06-30', NOW(), NOW())
    ON CONFLICT DO NOTHING;

INSERT INTO status_availability (description)
VALUES
    ('ENVIADO'),
    ('APROBADO'),
    ('RECHAZADO')
    ON CONFLICT DO NOTHING;

INSERT INTO level_subject (description) VALUES
                                            ('NIVEL 1'),
                                            ('NIVEL 2'),
                                            ('NIVEL 3'),
                                            ('NIVEL 4'),
                                            ('NIVEL 5'),
                                            ('NIVEL 6'),
                                            ('NIVEL 7'),
                                            ('NIVEL 8'),
                                            ('NIVEL 9'),
                                            ('NIVEL 10')
    ON CONFLICT DO NOTHING;
INSERT INTO area (description) VALUES
                                   ('MATEMÁTICAS COMPUTACIONALES'),
                                   ('ARQUITECTURA Y FUNCIONAMIENTO DEL COMPUTADOR'),
                                   ('ALGORÍTMICA E INFORMÁTICA'),
                                   ('ADMINISTRACIÓN DE LA INFORMACIÓN'),
                                   ('ADMINISTRATIVAS Y ORGANIZACIONALES'),
                                   ('REDES Y COMUNICACIONES'),
                                   ('INGENIERÍA DEL SOFTWARE'),
                                   ('SISTEMAS'),
                                   ('INGENIERÍA ARTIFICIAL'),
                                   ('ELECTIVAS')
    ON CONFLICT DO NOTHING;



INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'MATEMÁTICAS COMPUTACIONALES'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 3'),
           NULL,
           'MATEMÁTICAS DISCRETAS',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'MATEMÁTICAS COMPUTACIONALES'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 6'),
           NULL,
           'ESTADÍSTICA I',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'MATEMÁTICAS COMPUTACIONALES'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 7'),
           NULL,
           'ESTADÍSTICA II',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'MATEMÁTICAS COMPUTACIONALES'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 5'),
           NULL,
           'ANÁLISIS NUMÉRICO',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'ARQUITECTURA Y FUNCIONAMIENTO DEL COMPUTADOR'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 4'),
           NULL,
           'ELECTRICIDAD Y ELECTRÓNICA',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'ARQUITECTURA Y FUNCIONAMIENTO DEL COMPUTADOR'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 5'),
           NULL,
           'SISTEMAS DIGITALES',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'ARQUITECTURA Y FUNCIONAMIENTO DEL COMPUTADOR'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 6'),
           NULL,
           'ARQUITECTURA DE COMPUTADORES',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'ARQUITECTURA Y FUNCIONAMIENTO DEL COMPUTADOR'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 8'),
           NULL,
           'SISTEMAS OPERACIONALES',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'ARQUITECTURA Y FUNCIONAMIENTO DEL COMPUTADOR'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 10'),
           NULL,
           'SISTEMAS DISTRIBUIDOS',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'ALGORÍTMICA E INFORMÁTICA'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 1'),
           NULL,
           'FUNDAMENTOS DE PROGRAMACIÓN',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'ALGORÍTMICA E INFORMÁTICA'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 2'),
           NULL,
           'PROGRAMACIÓN ORIENTADA A OBJETOS',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'ALGORÍTMICA E INFORMÁTICA'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 3'),
           NULL,
           'ESTRUCTURA DE DATOS Y ANÁLISIS DE ALGORITMOS',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'ALGORÍTMICA E INFORMÁTICA'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 6'),
           NULL,
           'PROGRAMACIÓN EN LA WEB',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'ALGORÍTMICA E INFORMÁTICA'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 4'),
           NULL,
           'AUTÓMATAS Y LENGUAJES FORMALES',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'ALGORÍTMICA E INFORMÁTICA'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 10'),
           NULL,
           'ENTORNOS DE PROGRAMACIÓN',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'ALGORÍTMICA E INFORMÁTICA'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 10'),
           NULL,
           'PROGRAMACIÓN DISTRIBUIDA',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'ADMINISTRACIÓN DE LA INFORMACIÓN'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 4'),
           NULL,
           'BASE DE DATOS I',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'ADMINISTRACIÓN DE LA INFORMACIÓN'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 5'),
           NULL,
           'BASE DE DATOS II',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'ADMINISTRATIVAS Y ORGANIZACIONALES'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 5'),
           NULL,
           'PENSAMIENTO SISTÉMICO Y ORGANIZACIONAL',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'ADMINISTRATIVAS Y ORGANIZACIONALES'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 6'),
           NULL,
           'SISTEMAS DE INFORMACIÓN',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'ADMINISTRATIVAS Y ORGANIZACIONALES'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 10'),
           NULL,
           'AUDITORÍA DE SISTEMAS',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'REDES Y COMUNICACIONES'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 6'),
           NULL,
           'REDES DE COMPUTADORES I',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'REDES Y COMUNICACIONES'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 7'),
           NULL,
           'REDES DE COMPUTADORES II',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'REDES Y COMUNICACIONES'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 10'),
           NULL,
           'GESTIÓN DE REDES EMPRESARIALES',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'INGENIERÍA DEL SOFTWARE'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 7'),
           NULL,
           'INGENIERÍA DEL SOFTWARE I',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'INGENIERÍA DEL SOFTWARE'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 8'),
           NULL,
           'INGENIERÍA DEL SOFTWARE II',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'SISTEMAS'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 8'),
           NULL,
           'SIMULACIÓN DIGITAL',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'SISTEMAS'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 10'),
           NULL,
           'TRATAMIENTO DE SEÑALES',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'SISTEMAS'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 10'),
           NULL,
           'MODELADO ESTRUCTURAL',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'SISTEMAS'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 10'),
           NULL,
           'INVESTIGACIÓN OPERACIONAL',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'SISTEMAS'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 10'),
           NULL,
           'MODELOS A GRAN ESCALA',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'SISTEMAS'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 10'),
           NULL,
           'SISTEMAS DISCRETOS Y CONTINUOS',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'INGENIERÍA ARTIFICIAL'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 7'),
           NULL,
           'INGENIERÍA ARTIFICIAL I',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'INGENIERÍA ARTIFICIAL'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 10'),
           NULL,
           'INGENIERÍA ARTIFICIAL II',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'INGENIERÍA ARTIFICIAL'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 10'),
           NULL,
           'INGENIERÍA ARTIFICIAL III',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'INGENIERÍA ARTIFICIAL'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 10'),
           NULL,
           'MICROCONTROLADORES I',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'INGENIERÍA ARTIFICIAL'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 10'),
           NULL,
           'MICROCONTROLADORES II',
           NULL,
           NOW(), NOW()
       );
INSERT INTO subject (id_area, id_level_subject, codigo, name, max_students, creation_date, update_date)
VALUES (
           (SELECT id FROM area WHERE description = 'INGENIERÍA ARTIFICIAL'),
           (SELECT id FROM level_subject WHERE description = 'NIVEL 10'),
           NULL,
           'INFORMÁTICA BIOMÉDICA',
           NULL,
           NOW(), NOW()
       );

