CREATE DATABASE bdFXHibernate;

USE bdFXHibernate;

CREATE TABLE bdFXHibernate.User (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    nombre_usuario VARCHAR(50) NOT NULL,
                                    contraseña VARCHAR(255) NOT NULL,
                                    is_admin BOOLEAN NOT NULL
);

CREATE TABLE bdFXHibernate.Movie (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     titulo VARCHAR(255) NOT NULL,
                                     genero VARCHAR(50) NOT NULL,
                                     año INT NOT NULL,
                                     descripcion TEXT NOT NULL,
                                     director VARCHAR(255) NOT NULL
);

CREATE TABLE bdFXHibernate.Copia (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     id_usuario INT NOT NULL,
                                     id_pelicula INT NOT NULL,
                                     estado VARCHAR(50) NOT NULL,
                                     soporte VARCHAR(50) NOT NULL,
                                     FOREIGN KEY (id_usuario) REFERENCES User(id),
                                     FOREIGN KEY (id_pelicula) REFERENCES Movie(id)
);


INSERT INTO bdFXHibernate.Movie (id, titulo, genero, año, descripcion, director) VALUES
                                                                                     (1, 'Origen', 'Sci-Fi', 2010, 'Un ladrón que roba secretos corporativos a través del uso de la tecnología de compartir sueños recibe una oportunidad para borrar su historial criminal.', 'Christopher Nolan'),
                                                                                     (2, 'Matrix', 'Acción', 1999, 'Un hacker de ordenadores aprende de rebeldes misteriosos sobre la verdadera naturaleza de su realidad y su papel en la guerra contra sus controladores.', 'Lana Wachowski'),
                                                                                     (3, 'Interstellar', 'Sci-Fi', 2014, 'Un equipo de exploradores viaja a través de un agujero de gusano en el espacio en un intento de asegurar la supervivencia de la humanidad.', 'Christopher Nolan'),
                                                                                     (4, 'Star Wars: Episodio IV - Una nueva esperanza', 'Sci-Fi', 1977, 'Un joven granjero se une a una rebelión contra un imperio galáctico tiránico.', 'George Lucas'),
                                                                                     (5, 'El Señor de los Anillos: La Comunidad del Anillo', 'Aventura', 2001, 'Un hobbit se embarca en una misión para destruir un poderoso anillo.', 'Peter Jackson'),
                                                                                     (6, 'Gladiator', 'Acción', 2000, 'Un general romano caído en desgracia busca venganza contra el emperador corrupto que lo traicionó.', 'Ridley Scott'),
                                                                                     (7, 'Titanic', 'Romance', 1997, 'Una historia de amor prohibida entre una joven de clase alta y un artista pobre a bordo del trágico Titanic.', 'James Cameron'),
                                                                                     (8, 'El Padrino', 'Drama', 1972, 'La historia de una familia de la mafia siciliana en Nueva York.', 'Francis Ford Coppola'),
                                                                                     (9, 'El Caballero Oscuro', 'Acción', 2008, 'Batman se enfrenta a su mayor enemigo, el Joker, en una lucha por la ciudad de Gotham.', 'Christopher Nolan'),
                                                                                     (10, 'Pulp Fiction', 'Crimen', 1994, 'Historias entrelazadas de crimen en Los Ángeles.', 'Quentin Tarantino'),
                                                                                     (11, 'Forrest Gump', 'Drama', 1994, 'La vida extraordinaria de un hombre común con un corazón puro.', 'Robert Zemeckis'),
                                                                                     (12, 'El Rey León', 'Animación', 1994, 'Un joven león debe recuperar su reino después de la traición de su tío.', 'Roger Allers, Rob Minkoff'),
                                                                                     (13, 'Jurassic Park', 'Aventura', 1993, 'Un parque temático de dinosaurios clonados se convierte en un caos cuando las criaturas se escapan.', 'Steven Spielberg'),
                                                                                     (14, 'Avatar', 'Sci-Fi', 2009, 'Un marine parapléjico en el planeta Pandora se enfrenta a un dilema moral entre su misión y su nuevo hogar.', 'James Cameron'),
                                                                                     (15, 'El Resplandor', 'Horror', 1980, 'Un hombre pierde su cordura mientras cuida de un hotel aislado en invierno.', 'Stanley Kubrick'),
                                                                                     (16, 'La La Land', 'Musical', 2016, 'La historia de amor entre una actriz en ciernes y un pianista de jazz en Los Ángeles.', 'Damien Chazelle'),
                                                                                     (17, 'La Lista de Schindler', 'Drama', 1993, 'La historia real de Oskar Schindler y su esfuerzo por salvar vidas durante el Holocausto.', 'Steven Spielberg'),
                                                                                     (18, 'Django Unchained', 'Western', 2012, 'Un esclavo liberado se embarca en una búsqueda para salvar a su esposa.', 'Quentin Tarantino'),
                                                                                     (19, 'El Gran Lebowski', 'Comedia', 1998, 'Un hombre común llamado "El Nota" se ve involucrado en un secuestro por error.', 'Joel Coen'),
                                                                                     (20, 'Amélie', 'Romance', 2001, 'Una joven soñadora en París decide ayudar a los demás de manera creativa.', 'Jean-Pierre Jeunet'),
                                                                                     (21, 'Infiltrados', 'Crimen', 2006, 'Un policía infiltrado en la mafia y un criminal infiltrado en la policía deben desenmascararse mutuamente.', 'Martin Scorsese'),
                                                                                     (22, 'Snatch', 'Comedia', 2000, 'Diversas historias de crimen y caos en el submundo de Londres.', 'Guy Ritchie'),
                                                                                     (23, 'Kill Bill: Volumen 1', 'Acción', 2003, 'Una ex asesina busca venganza contra aquellos que intentaron matarla.', 'Quentin Tarantino'),
                                                                                     (24, 'El Laberinto del Fauno', 'Fantasía', 2006, 'Una niña se encuentra con un fauno en una España franquista mítica.', 'Guillermo del Toro'),
                                                                                     (25, 'Scarface', 'Crimen', 1983, 'La historia de un refugiado cubano que asciende al poder en el submundo de las drogas en Miami.', 'Brian De Palma'),
                                                                                     (26, 'El Pianista', 'Drama', 2002, 'La historia de supervivencia de un pianista polaco durante la Segunda Guerra Mundial.', 'Roman Polanski'),
                                                                                     (27, 'La Milla Verde', 'Drama', 1999, 'La historia de un guardia de prisión y un prisionero con habilidades sobrenaturales.', 'Frank Darabont'),
                                                                                     (28, 'Toy Story', 'Animación', 1995, 'Un juguete siente celos cuando un nuevo juguete se convierte en el favorito de su dueño.', 'John Lasseter'),
                                                                                     (29, 'El Sexto Sentido', 'Suspenso', 1999, 'Un niño que ve espíritus busca la ayuda de un psicólogo.', 'M. Night Shyamalan'),
                                                                                     (30, 'Casino', 'Crimen', 1995, 'La historia del ascenso y caída de un gerente de casino en Las Vegas.', 'Martin Scorsese'),
                                                                                     (31, 'American Beauty', 'Drama', 1999, 'Un hombre sufre una crisis de la mediana edad que lo lleva a cuestionar su vida y decisiones.', 'Sam Mendes'),
                                                                                     (32, 'El Bueno, el Feo y el Malo', 'Western', 1966, 'Tres forajidos compiten para encontrar un tesoro escondido en medio de la guerra civil americana.', 'Sergio Leone'),
                                                                                     (33, 'Salvar al Soldado Ryan', 'Bélico', 1998, 'Un equipo de soldados se embarca en una misión para rescatar a un paracaidista tras la invasión de Normandía.', 'Steven Spielberg'),
                                                                                     (34, 'Los Intocables', 'Crimen', 1987, 'Un agente del Tesoro lucha para derribar al infame Al Capone durante la era de la Prohibición.', 'Brian De Palma'),
                                                                                     (35, '12 Monos', 'Sci-Fi', 1995, 'Un hombre es enviado al pasado para detener una pandemia.', 'Terry Gilliam'),
                                                                                     (36, 'El Club de la Lucha', 'Drama', 1999, 'Un hombre crea un club de lucha clandestino como una forma de enfrentarse a la vida moderna.', 'David Fincher'),
                                                                                     (37, 'Bastardos sin Gloria', 'Bélico', 2009, 'Un grupo de soldados americanos judíos intenta acabar con el régimen nazi en la Francia ocupada.', 'Quentin Tarantino'),
                                                                                     (38, 'Tiempos Violentos', 'Crimen', 1994, 'Historias entrelazadas de crimen en el submundo de Los Ángeles.', 'Quentin Tarantino'),
                                                                                     (39, '300', 'Acción', 2006, 'La batalla de las Termópilas y el sacrificio de 300 espartanos.', 'Zack Snyder'),
                                                                                     (40, 'La Jungla de Cristal', 'Acción', 1988, 'Un policía de Nueva York se enfrenta a terroristas en un rascacielos en Los Ángeles.', 'John McTiernan');
INSERT INTO bdFXHibernate.User (id, nombre_usuario, contraseña, is_admin) VALUES
                                                                              (1, 'juanperez', 'password123',TRUE),
                                                                              (2, 'mariagonzalez', 'mypassword',FALSE),
                                                                              (3, 'pedrolopez', 'pass123', FALSE),
                                                                              (4, 'angelagarcia', 'secret987', FALSE),
                                                                              (5, 'luishernandez', 'mysecurepass', TRUE),
                                                                              (6, 'carolinamartinez', 'passcarolina', FALSE),
                                                                              (7, 'fernandojimenez', 'fernando2023', FALSE),
                                                                              (8, 'lauragonzalez', 'passwordlaura', TRUE),
                                                                              (9, 'carlosfernandez', 'carlospass', FALSE),
                                                                              (10, 'andreadiaz', 'andreadiazz', FALSE),
                                                                              (11, 'robertohidalgo', 'robpass123', FALSE),
                                                                              (12, 'beatrizlopez', 'beatpass', FALSE);

INSERT INTO bdFXHibernate.Copia (id, id_usuario, id_pelicula, estado, soporte) VALUES
                                                                                   (1, 1, 1, 'bueno', 'DVD'),
                                                                                   (2, 1, 1, 'bueno', 'Blu-ray'),
                                                                                   (3, 2, 2, 'dañado', 'DVD'),
                                                                                   (4, 3, 1, 'bueno', 'Blu-ray'),
                                                                                   (5, 4, 2, 'bueno', 'DVD'),
                                                                                   (6, 4, 1, 'bueno', 'Blu-ray'),
                                                                                   (7, 2, 3, 'bueno', 'DVD'),
                                                                                   (8, 3, 4, 'bueno', 'Blu-ray'),
                                                                                   (9, 1, 5, 'dañado', 'DVD'),
                                                                                   (10, 2, 6, 'bueno', 'DVD'),
                                                                                   (11, 3, 7, 'bueno', 'Blu-ray'),
                                                                                   (12, 4, 8, 'bueno', 'DVD'),
                                                                                   (13, 1, 9, 'dañado', 'Blu-ray'),
                                                                                   (14, 2, 10, 'bueno', 'DVD'),
                                                                                   (15, 3, 11, 'bueno', 'Blu-ray'),
                                                                                   (16, 4, 12, 'bueno', 'DVD'),
                                                                                   (17, 1, 13, 'bueno', 'Blu-ray'),
                                                                                   (18, 2, 14, 'dañado', 'DVD'),
                                                                                   (19, 3, 15, 'bueno', 'DVD'),
                                                                                   (20, 4, 16, 'bueno', 'Blu-ray'),
                                                                                   (21, 1, 17, 'bueno', 'DVD'),
                                                                                   (22, 2, 18, 'bueno', 'Blu-ray'),
                                                                                   (23, 3, 19, 'dañado', 'DVD'),
                                                                                   (24, 4, 20, 'bueno', 'Blu-ray'),
                                                                                   (25, 1, 21, 'bueno', 'DVD'),
                                                                                   (26, 2, 22, 'bueno', 'Blu-ray'),
                                                                                   (27, 3, 23, 'bueno', 'DVD'),
                                                                                   (28, 4, 24, 'dañado', 'Blu-ray'),
                                                                                   (29, 1, 25, 'bueno', 'DVD'),
                                                                                   (30, 2, 26, 'bueno', 'Blu-ray'),
                                                                                   (31, 3, 27, 'dañado', 'DVD'),
                                                                                   (32, 4, 28, 'bueno', 'Blu-ray'),
                                                                                   (33, 1, 29, 'bueno', 'DVD'),
                                                                                   (34, 2, 30, 'bueno', 'Blu-ray'),
                                                                                   (35, 3, 31, 'bueno', 'DVD'),
                                                                                   (36, 4, 32, 'bueno', 'Blu-ray'),
                                                                                   (37, 1, 33, 'dañado', 'DVD'),
                                                                                   (38, 2, 34, 'bueno', 'Blu-ray'),
                                                                                   (39, 3, 35, 'bueno', 'DVD'),
                                                                                   (40, 4, 36, 'bueno', 'Blu-ray'),
                                                                                   (41, 1, 37, 'bueno', 'DVD'),
                                                                                   (42, 2, 38, 'dañado', 'Blu-ray'),
                                                                                   (43, 3, 39, 'bueno', 'DVD'),
                                                                                   (44, 4, 40, 'bueno', 'Blu-ray'),
                                                                                   (45, 1, 5, 'bueno', 'DVD'),
                                                                                   (46, 2, 6, 'bueno', 'Blu-ray');

ALTER TABLE Movie
    ADD COLUMN cover VARCHAR(255),
    ADD COLUMN url VARCHAR(255);
UPDATE Movie SET cover = '1.jpg' WHERE id = 1;
UPDATE Movie SET cover = '2.jpg' WHERE id = 2;
UPDATE Movie SET cover = '3.jpg' WHERE id = 3;
UPDATE Movie SET cover = '4.jpg' WHERE id = 4;
UPDATE Movie SET cover = '5.jpg' WHERE id = 5;
UPDATE Movie SET cover = '6.jpg' WHERE id = 6;
UPDATE Movie SET cover = '7.jpg' WHERE id = 7;
UPDATE Movie SET cover = '8.jpg' WHERE id = 8;
UPDATE Movie SET cover = '9.jpg' WHERE id = 9;
UPDATE Movie SET cover = '10.jpg' WHERE id = 10;
UPDATE Movie SET cover = '11.jpg' WHERE id = 11;
UPDATE Movie SET cover = '12.jpg' WHERE id = 12;
UPDATE Movie SET cover = '13.jpg' WHERE id = 13;
UPDATE Movie SET cover = '14.jpg' WHERE id = 14;
UPDATE Movie SET cover = '15.jpg' WHERE id = 15;
UPDATE Movie SET cover = '16.jpg' WHERE id = 16;
UPDATE Movie SET cover = '17.jpg' WHERE id = 17;
UPDATE Movie SET cover = '18.jpg' WHERE id = 18;
UPDATE Movie SET cover = '19.jpg' WHERE id = 19;
UPDATE Movie SET cover = '20.jpg' WHERE id = 20;
UPDATE Movie SET cover = '21.jpg' WHERE id = 21;
UPDATE Movie SET cover = '22.jpg' WHERE id = 22;
UPDATE Movie SET cover = '23.jpg' WHERE id = 23;
UPDATE Movie SET cover = '24.jpg' WHERE id = 24;
UPDATE Movie SET cover = '25.jpg' WHERE id = 25;
UPDATE Movie SET cover = '26.jpg' WHERE id = 26;
UPDATE Movie SET cover = '27.jpg' WHERE id = 27;
UPDATE Movie SET cover = '28.jpg' WHERE id = 28;
UPDATE Movie SET cover = '29.jpg' WHERE id = 29;
UPDATE Movie SET cover = '30.jpg' WHERE id = 30;
UPDATE Movie SET cover = '31.jpg' WHERE id = 31;
UPDATE Movie SET cover = '32.jpg' WHERE id = 32;
UPDATE Movie SET cover = '33.jpg' WHERE id = 33;
UPDATE Movie SET cover = '34.jpg' WHERE id = 34;
UPDATE Movie SET cover = '35.jpg' WHERE id = 35;
UPDATE Movie SET cover = '36.jpg' WHERE id = 36;
UPDATE Movie SET cover = '37.jpg' WHERE id = 37;
UPDATE Movie SET cover = '38.jpg' WHERE id = 38;
UPDATE Movie SET cover = '39.jpg' WHERE id = 39;
UPDATE Movie SET cover = '40.jpg' WHERE id = 40;


UPDATE Movie SET url = CASE id
                           WHEN 1 THEN 'https://www.youtube.com/embed/RV9L7ui9Cn8?fs=1'
                           WHEN 2 THEN 'https://www.youtube.com/embed/Pl_H2Lmjn6k?fs=1'
                           WHEN 3 THEN 'https://www.youtube.com/embed/hhCtMhk8eHo?fs=1'
                           WHEN 4 THEN 'https://www.youtube.com/embed/beAH5vea99k?fs=1'
                           WHEN 5 THEN 'https://www.youtube.com/embed/iFOucwxKRFE?fs=1'
                           WHEN 6 THEN 'https://www.youtube.com/embed/Pb6WME02hdA?fs=1'
                           WHEN 7 THEN 'https://www.youtube.com/embed/wMZuro21wtE?fs=1'
                           WHEN 8 THEN 'https://www.youtube.com/embed/XLEnSN99ths?fs=1'
                           WHEN 9 THEN 'https://www.youtube.com/embed/Qs-NylETt1E?fs=1'
                           WHEN 10 THEN 'https://www.youtube.com/embed/ZFYCXAG6fdo?fs=1'
                           WHEN 11 THEN 'https://www.youtube.com/embed/Cyh1LpxnaxI?fs=1'
                           WHEN 12 THEN 'https://www.youtube.com/embed/bjJvvrKl83M?fs=1'
                           WHEN 13 THEN 'https://www.youtube.com/embed/dLDkNge_AhE?fs=1'
                           WHEN 14 THEN 'https://www.youtube.com/embed/CpXJHWSXJW0?fs=1'
                           WHEN 15 THEN 'https://www.youtube.com/embed/f7ADL4i_eCQ?fs=1'
                           WHEN 16 THEN 'https://www.youtube.com/embed/nRayUjXIDdQ?fs=1'
                           WHEN 17 THEN 'https://www.youtube.com/embed/7q-ETFeMxwI?fs=1'
                           WHEN 18 THEN 'https://www.youtube.com/embed/CLofzNkIqAc?fs=1'
                           WHEN 19 THEN 'https://www.youtube.com/embed/3P8hLsPMPug?fs=1'
                           WHEN 20 THEN 'https://www.youtube.com/embed/JMPOXWSL_PY?fs=1'
                           WHEN 21 THEN 'https://www.youtube.com/embed/VSKapBl__oU?fs=1'
                           WHEN 22 THEN 'https://www.youtube.com/embed/IXYy07RTMnI?fs=1'
                           WHEN 23 THEN 'https://www.youtube.com/embed/otmfu72n2MU?fs=1'
                           WHEN 24 THEN 'https://www.youtube.com/embed/FGzvvUBXj5M?fs=1'
                           WHEN 25 THEN 'https://www.youtube.com/embed/Q7TbPvMBmzM?fs=1'
                           WHEN 26 THEN 'https://www.youtube.com/embed/yDA1mK6v-ME?fs=1'
                           WHEN 27 THEN 'https://www.youtube.com/embed/hBtSF4-cnzk?fs=1'
                           WHEN 28 THEN 'https://www.youtube.com/embed/VhCDgv4x_pU?fs=1'
                           WHEN 29 THEN 'https://www.youtube.com/embed/zfOdk9JDzSw?fs=1'
                           WHEN 30 THEN 'https://www.youtube.com/embed/aFP8CjozpkY?fs=1'
                           WHEN 31 THEN 'https://www.youtube.com/embed/Ly7rq5EsTC8?fs=1'
                           WHEN 32 THEN 'https://www.youtube.com/embed/exJOy6uTkek?fs=1'
                           WHEN 33 THEN 'https://www.youtube.com/embed/Y3dH5bBt1sw?fs=1'
                           WHEN 34 THEN 'https://www.youtube.com/embed/h0u2CYKXlGo?fs=1'
                           WHEN 35 THEN 'https://www.youtube.com/embed/tSQonjDo9Jk?fs=1'
                           WHEN 36 THEN 'https://www.youtube.com/embed/r5KDiUNZv4w?fs=1'
                           WHEN 37 THEN 'https://www.youtube.com/embed/2jcTRi8D80k?fs=1'
                           WHEN 38 THEN 'https://www.youtube.com/embed/ZFYCXAG6fdo?fs=1'
                           WHEN 39 THEN 'https://www.youtube.com/embed/mi4xUUJSuro?fs=1'
                           WHEN 40 THEN 'https://www.youtube.com/embed/GAdvN7KLc2o?fs=1'
                           ELSE url
    END
WHERE id IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40);