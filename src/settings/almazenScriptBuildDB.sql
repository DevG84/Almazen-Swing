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
('1', 'Admin', 'Administrador', NULL, NULL, 'Administrador', NULL, '17353537');

INSERT INTO privilegios (IDusuario, status, modBuscar, modInOut, modConsulta, modPerCod, modAlterUsuarios) VALUES
('1', 'S', 'S', 'S', 'S', 'S', 'S');

ALTER TABLE movimiento
  ADD CONSTRAINT FK_IDmercancia FOREIGN KEY (IDmercancia) REFERENCES mercancia (IDmercancia),
  ADD CONSTRAINT FK_IDusuario FOREIGN KEY (IDusuario) REFERENCES usuarios (IDusuario);

ALTER TABLE privilegios
  ADD CONSTRAINT FK_privilegios FOREIGN KEY (IDusuario) REFERENCES usuarios (IDusuario);
