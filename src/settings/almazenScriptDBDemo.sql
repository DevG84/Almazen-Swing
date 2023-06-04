DROP TABLE IF EXISTS mercancia;
CREATE TABLE IF NOT EXISTS mercancia (
  IDmercancia int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  codigo varchar(50) NOT NULL,
  articulo varchar(255) DEFAULT NULL,
  descripcion varchar(300) DEFAULT NULL,
  marca varchar(50) DEFAULT NULL,
  presentacion varchar(35) DEFAULT NULL,
  existencia int(9) UNSIGNED DEFAULT NULL,
  almacen varchar(40) NOT NULL,
  anaquel varchar(20) DEFAULT NULL,
  repisa varchar(20) DEFAULT NULL,
  PRIMARY KEY (IDmercancia)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS movimiento;
CREATE TABLE IF NOT EXISTS movimiento (
  IDmovimiento int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  IDusuario int(10) UNSIGNED DEFAULT NULL,
  IDmercancia int(10) UNSIGNED DEFAULT NULL,
  tipo enum('E','S','U') DEFAULT NULL,
  asunto varchar(255) DEFAULT NULL,
  fecha date DEFAULT NULL,
  cantidad int(9) UNSIGNED DEFAULT 0,
  PRIMARY KEY (IDmovimiento),
  KEY FK_IDusuario (IDusuario),
  KEY FK_IDmercancia (IDmercancia)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS privilegios;
CREATE TABLE IF NOT EXISTS privilegios (
  IDusuario int(10) UNSIGNED DEFAULT NULL,
  status enum('S','N') DEFAULT 'S',
  modBuscar enum('S','N') DEFAULT NULL,
  modInOut enum('S','N') DEFAULT NULL,
  modConsulta enum('S','N') DEFAULT NULL,
  modPerCod enum('S','N') DEFAULT NULL,
  modAlterUsuarios enum('S','N') DEFAULT 'N',
  KEY FK_usuarios (IDusuario)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS usuarios;
CREATE TABLE IF NOT EXISTS usuarios (
  IDusuario int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  nickname varchar(35) DEFAULT NULL,
  nombre varchar(35) DEFAULT NULL,
  paterno varchar(35) DEFAULT NULL,
  materno varchar(35) DEFAULT NULL,
  cargo varchar(35) DEFAULT NULL,
  boleta varchar(35) DEFAULT NULL,
  password varchar(50) DEFAULT NULL,
  PRIMARY KEY (IDusuario)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

ALTER TABLE mercancia AUTO_INCREMENT = 1;

ALTER TABLE movimiento AUTO_INCREMENT = 1;

ALTER TABLE usuarios AUTO_INCREMENT = 1;

INSERT INTO usuarios (IDusuario, nickname, nombre, paterno, materno, cargo, boleta, password) VALUES
(1, 'Admin', 'Administrador', NULL, NULL, 'Administrador', NULL, '17353537'),
(2, 'Gio', 'Giovanni', 'Hernández', 'Cruz', 'Encargado de almacén', '2021140176', '0323355573'),
(3, 'Martin', 'Martin', 'Gonzalez', 'Martinez', 'Servicio Social', '2021140159', '050117372315'),
(4, 'Adrien', 'Adrian', 'Gonzalez', 'Hernandez', 'Invitado', '2021140178', '013117230115'),
(5, 'jose', 'Jose', 'Peña', 'Vega', 'Servicio Social', '1234567891', '33352741'),
(6, 'Emy', 'Emily', 'Leija', 'Gonzalez', 'Servicio Social', '2021140123', '4105235376'),
(7, 'Mirabella', 'Mirabel', 'Guadalupe', 'Victoria', 'Invitado', '202154889', '05231701114153'),
(8, 'Alex', 'Alexander', 'Jimenez', 'Robles', 'Invitado', '2021152369', '01534174'),
(9, 'Lau', 'Laura', 'Zamorano', 'Robles', 'Encargado de almacén', '1234567411', '5301471701'),
(10, 'micasita', 'Llendo a', 'la casa', 'de Damián', 'Encargado de almacén', '214578963', '310105230115'),
(11, 'Juanito', 'Juan', 'Jimenez', 'Martinez', 'Servicio Social', '5524701', '3347011555565758'),
(12, 'Cajita', 'Cajita', 'Mc', 'Happy', 'Encargado de almacén', '0000101011', '210133233701'),
(13, 'Maribel', 'Maribel', 'Sanchez', 'Ortega', 'Servicio Social', '211111111111123', '05011723114153'),
(14, 'Ale', 'Ale', 'Ale', 'Ale', 'Servicio Social', '202121', '015341330115311735'),
(15, 'Javi', 'Javier', 'Martinez', 'Sanchez', 'Servicio Social', '1212', '330175234117'),
(16, 'Esteban', 'Esteban', 'Olvera', 'Ortiz', 'Servicio Social', '416546516', '41273741110115'),
(17, 'Andres', 'Andres', 'Perez', 'Camacho', 'Servicio Social', '21014', '011531174127');
;

INSERT INTO privilegios (IDusuario, status, modBuscar, modInOut, modConsulta, modPerCod, modAlterUsuarios) VALUES
(1, 'S', 'S', 'S', 'S', 'S', 'S'),
(2, 'S', 'S', 'S', 'S', 'S', 'S'),
(3, 'S', 'N', 'N', 'N', 'N', 'N'),
(4, 'S', 'N', 'N', 'N', 'N', 'N'),
(5, 'S', 'S', 'S', 'N', 'N', 'N'),
(6, 'S', 'S', 'S', 'S', 'S', 'N'),
(7, 'S', 'S', 'S', 'N', 'N', 'N'),
(8, 'S', 'S', 'S', 'N', 'S', 'N'),
(9, 'S', 'S', 'S', 'S', 'S', 'N'),
(10, 'S', 'S', 'S', 'N', 'N', 'N'),
(11, 'S', 'S', 'S', 'S', 'N', 'N'),
(12, 'S', 'S', 'S', 'S', 'S', 'N'),
(13, 'S', 'S', 'S', 'S', 'N', 'N'),
(14, 'S', 'S', 'S', 'S', 'N', 'N'),
(15, 'S', 'S', 'S', 'S', 'N', 'N'),
(16, 'S', 'S', 'S', 'S', 'N', 'N'),
(17, 'S', 'S', 'S', 'S', 'N', 'N')
;

ALTER TABLE movimiento
  ADD CONSTRAINT FK_IDmercancia FOREIGN KEY (IDmercancia) REFERENCES mercancia (IDmercancia),
  ADD CONSTRAINT FK_IDusuario FOREIGN KEY (IDusuario) REFERENCES usuarios (IDusuario);

ALTER TABLE privilegios
  ADD CONSTRAINT FK_privilegios FOREIGN KEY (IDusuario) REFERENCES usuarios (IDusuario);
