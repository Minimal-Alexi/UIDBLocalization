DROP DATABASE IF EXISTS job_localization;
CREATE DATABASE job_localization;

CREATE TABLE language(
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         language_code VARCHAR(10) NOT NULL,
                         country_code VARCHAR(10) NOT NULL
);
CREATE TABLE job_title(
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL
);
CREATE TABLE job_translation(
                                language_id INT NOT NULL ,
                                job_id INT NOT NULL ,
                                translation_text VARCHAR(100) NOT NULL,
                                UNIQUE KEY (language_id,job_id),
                                FOREIGN KEY (job_id) REFERENCES job_title (id),
                                FOREIGN KEY (language_id) REFERENCES language (id)
);

INSERT INTO language(id, language_code, country_code)
VALUES (1,'en','US'),
       (2,'es','ES'),
       (3,'fr','FR'),
       (4,'zh','CN');

INSERT INTO job_title(id, name)
VALUES (1,'Manager'),
       (2,'Janitor'),
       (3, 'Programmer');

INSERT INTO job_translation(language_id, job_id, translation_text)
VALUES (4,1,'经理'),
       (3,1,'Directeur'),
       (2,1,'Gerente'),
       (4,2,'看门人'),
       (3,2,'Concierge'),
       (2,2,'Conserje'),
       (4,3,'程序员'),
       (3,3,'Programmeur'),
       (2,3,'Programador');
