DROP DATABASE IF EXISTS polideportivos	;

CREATE DATABASE IF NOT EXISTS polideportivos;
USE polideportivos;

CREATE TABLE IF NOT EXISTS polideportivos
(
	idPolideportivos INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(50) UNIQUE NOT NULL,
	direccion VARCHAR(50) NOT NULL
);
CREATE TABLE IF NOT EXISTS pista
(
	idPista INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	codigo VARCHAR(10) NOT NULL,
	operativa BOOLEAN DEFAULT TRUE,
	id_polideportivo INT UNSIGNED NOT NULL 
		REFERENCES polideportivos
);
CREATE TABLE IF NOT EXISTS reservas
(
	idReservas INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	precio FLOAT DEFAULT 0,
	id_pista INT UNSIGNED NOT NULL REFERENCES pistas
);
/* INSERTAR DATOS NAS TABLAS */

INSERT INTO `polideportivos` (`idPolideportivos`, `nombre`,`direccion` ) VALUES 
(1,'Polideportivo de arriba','Plaza Roja'),
(2,'Polideportivo de abaixo','Plaza de la Rosa'),
(3, 'Frontón','Calle del Sol'),
(4,'Rojiblanco','Avenida de la Libertad'),
(5, 'Grande','Calle Mayor'),
(6,'Pequeño', 'Paseo de la Playa'),
(7,'Verde', 'Calle de la Luna'),
(8, 'Figueiroa','Plaza del Parque'),
(9, 'Codeseda','Avenida del Mar'),
(10, 'A Estrada', 'Plaza de la Rosa');

SELECT * FROM polideportivos;

INSERT INTO `pista` (`idPista`, `codigo`, `operativa`, `id_polideportivo`) VALUES
(1, '1111', TRUE, 10),
(2, '2222',FALSE, 4),
(3, '3333', TRUE, 2),
(4, '4444', FALSE, 7),
(5, '5555', TRUE, 6),
(6, '6666', TRUE, 2),
(7, '7777', TRUE, 5),
(8, '8888', TRUE, 9),
(9, '9999', FALSE, 10);

SELECT * FROM pista;

INSERT INTO `reservas` (`idReservas`, `precio`, `id_pista`) VALUES
(1, 150.3, 1),
(2, 123.0, 9),
(3, 6423.45, 8),
(4, 20.12, 3),
(5, 132.99, 4),
(6, 2.78, 1),
(7, 167.43, 3),
(8, 987.36, 1),
(9, 2344.65, 2),
(10, 123.78, 7),
(11, 867.65, 1),
(12, 4354.45, 5),
(13, 3150.03, 5),
(14, 47.85, 6),
(15, 20.54, 3);

SELECT * FROM reservas;


DELIMITER $$

DROP PROCEDURE IF EXISTS pr_CodigoPrezo $$
CREATE PROCEDURE pr_CodigoPrezo (
    IN codigo VARCHAR(10))
BEGIN
SELECT r.precio
FROM reservas r
         INNER JOIN pista p ON r.id_pista = p.idPista
WHERE p.codigo = codigo;
END $$
DELIMITER ;



