--
-- Estructura de tabla para la tabla `mercancia`
--

DROP TABLE IF EXISTS `mercancia`;
CREATE TABLE IF NOT EXISTS `mercancia` (
  `IDmercancia` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `codigo` varchar(50) NOT NULL,
  `articulo` varchar(255) DEFAULT NULL,
  `descripcion` varchar(300) DEFAULT NULL,
  `marca` varchar(50) DEFAULT NULL,
  `presentacion` varchar(35) DEFAULT NULL,
  `existencia` int(9) UNSIGNED DEFAULT NULL,
  `almacen` varchar(40) NOT NULL,
  `anaquel` varchar(20) DEFAULT NULL,
  `repisa` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`IDmercancia`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `mercancia`
--

INSERT INTO `mercancia` (`IDmercancia`, `codigo`, `articulo`, `descripcion`, `marca`, `presentacion`, `existencia`, `almacen`, `anaquel`, `repisa`) VALUES
(1, '21200718632', 'POST-IT', 'Banderitas de colores 11.9mm x 43.2mm', 'Post-It', 'Pieza', 4, 'Zona 100 (Principal)', '2', '3'),
(2, '6942707401275', 'POST-IT', 'Banderitas separadoras de colores 44mm x 12mm', 'Post-It', 'Paquete', 24, 'Zona 100 (Principal)', '2', '3'),
(3, '7501357015806', 'Broche', 'Broche para archivo 8cm', 'ACCO', 'Caja', 10, 'Zona 100 (Principal)', '3', '3'),
(4, '7502285230453', 'Calculadora', 'Calculadora de plástico básica de 12 dígitos\n', 'MANNY', 'Pieza', 5, 'Zona 100 (Bodega)', '1', '3'),
(5, '7503017021257', 'Carpeta', 'Carpeta pánoramica de argollas 4\"', 'KYMA', 'Pieza', 10, 'Zona 100 (Principal)', '9', '3'),
(6, '7501887529231', 'Cinta gris', 'Cinta gris para ducto 48mm x 50m Offiland\n', 'Offiland', 'Pieza', 18, 'Zona 100 (Principal)', '5', '4'),
(7, '7501449717489', 'Cojín para sellos', 'Cojín para sellos No. 2', 'Stafford', 'Pieza', 5, 'Zona 100 (Principal)', '3', '3'),
(8, '9023800833013', 'Corrector líquido', 'Corrector líquido de tipo lápiz ', 'Kores', 'Pieza', 23, 'Zona 100 (Principal)', '2', '4'),
(9, '7502005165034', 'Despachador de cinta', 'Despachador grande para cinta de escritorio', 'KW', 'Pieza', 8, 'Zona 100 (Bodega)', '2', '1'),
(10, '7503002196014', 'Lubricante limpiador', 'Lubricante limpiador dieléctrico SILIJET E2', 'SiliJet', 'Lata', 4, 'Zona 100 (Principal)', 'UDI', '3'),
(11, '7501428722923', 'Marcatextos', 'Marcatextos rosa resaltador Fluorescent Highlighter Visión Plus ', 'Azor', 'Pieza', 84, 'Zona 100 (Principal)', '4', '2'),
(12, '7501943494220', 'Toalla sanitaria', 'Toalla sanitaria Kotex flujo súper abundante', 'Kotex', 'Pieza', 300, 'Zona 100 (Principal)', '9', '4'),
(13, '7501206673911', 'Banda de seguridad', 'Banda roja \"PELIGRO\" 7.5cm X 304m', 'Trupper', 'Pieza', 9, 'Zona 100 (Principal)', '5', '4'),
(14, '7502005003282', 'Alcohol Isopropilico', 'Alcohol Isopropilico para componente electrónicos 1L', 'Química Jerez', 'Botella', 7, 'Zona 100 (Principal)', '3', '2'),
(15, 'ALMZ-1', 'Ligas', 'Ligas de hule Hércules 75', 'Hércules', 'Paquete', 2, 'Zona 100 (Principal)', '3', '3'),
(16, 'ALMZ-2', 'Listón', 'Listón portacredencial', 'S/M', 'Pieza', 24, 'Zona 100 (Principal)', '4', '3'),
(17, 'ALMZ-3', 'Cinta canela', 'Cinta canela 48mm x 150m', 'Leader', 'Pieza', 15, 'Zona 100 (Bodega)', '2', '1'),
(18, '7501281908458', 'Vale provisional de caja', 'Vale provisional de cajaA B-2051 1/4 Carta\n', 'PCForm', 'Block', 5, 'Zona 100 (Principal)', '3', '2'),
(19, '7501214958031 ', 'Tijeras', 'Tijeras de acero inoxidable para oficina 15cm', 'Delta', 'Pieza', 12, 'Zona 100 (Principal)', '4', '3'),
(20, '7501449715034', 'Tinta para sellos', 'Tinta para sellos roja', 'Stafford', 'Pieza', 17, 'Zona 100 (Principal)', '3', '2');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimiento`
--

DROP TABLE IF EXISTS `movimiento`;
CREATE TABLE IF NOT EXISTS `movimiento` (
  `IDmovimiento` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `IDusuario` int(10) UNSIGNED DEFAULT NULL,
  `IDmercancia` int(10) UNSIGNED DEFAULT NULL,
  `tipo` enum('E','S','U') DEFAULT NULL,
  `asunto` varchar(255) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `cantidad` int(9) UNSIGNED DEFAULT '0',
  PRIMARY KEY (`IDmovimiento`),
  KEY `FK_IDusuario` (`IDusuario`),
  KEY `FK_IDmercancia` (`IDmercancia`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `movimiento`
--

INSERT INTO `movimiento` (`IDmovimiento`, `IDusuario`, `IDmercancia`, `tipo`, `asunto`, `fecha`, `cantidad`) VALUES
(1, 2, 1, 'E', 'Nuevo registro de material', '2023-06-04', 4),
(2, 2, 2, 'E', 'Nuevo registro de material', '2023-06-04', 24),
(3, 2, 3, 'E', 'Nuevo registro de material', '2023-06-04', 13),
(4, 2, 4, 'E', 'Nuevo registro de material', '2023-06-04', 5),
(5, 2, 5, 'E', 'Nuevo registro de material', '2023-06-04', 3),
(6, 2, 6, 'E', 'Nuevo registro de material', '2023-06-04', 18),
(7, 2, 7, 'E', 'Nuevo registro de material', '2023-06-04', 5),
(8, 2, 8, 'E', 'Nuevo registro de material', '2023-06-04', 23),
(9, 5, 9, 'E', 'Nuevo registro de material', '2023-06-04', 8),
(10, 5, 10, 'E', 'Nuevo registro de material', '2023-06-04', 4),
(11, 5, 11, 'E', 'Nuevo registro de material', '2023-06-04', 84),
(12, 2, 5, 'U', 'Actualización de datos del material.', '2023-06-04', 3),
(13, 2, 10, 'U', 'Actualización de datos del material.', '2023-06-04', 4),
(14, 2, 2, 'U', 'Actualización de datos del material.', '2023-06-04', 24),
(15, 2, 1, 'U', 'Actualización de datos del material.', '2023-06-04', 4),
(16, 2, 12, 'E', 'Nuevo registro de material', '2023-06-04', 300),
(17, 2, 13, 'E', 'Nuevo registro de material', '2023-06-04', 9),
(18, 2, 14, 'E', 'Nuevo registro de material', '2023-06-04', 2),
(19, 2, 15, 'E', 'Nuevo registro de material', '2023-06-04', 2),
(20, 2, 16, 'E', 'Nuevo registro de material', '2023-06-04', 24),
(21, 2, 17, 'E', 'Nuevo registro de material', '2023-06-04', 15),
(22, 2, 18, 'E', 'Nuevo registro de material', '2023-06-04', 5),
(23, 2, 19, 'E', 'Nuevo registro de material', '2023-06-04', 7),
(24, 2, 20, 'E', 'Nuevo registro de material', '2023-06-04', 17),
(25, 2, 5, 'E', 'Vale  15', '2023-06-04', 7),
(26, 2, 3, 'S', 'Vale  15', '2023-06-04', 3),
(27, 2, 14, 'E', 'Vale  15', '2023-06-04', 5),
(28, 2, 19, 'E', 'Vale 18', '2023-06-04', 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `privilegios`
--

DROP TABLE IF EXISTS `privilegios`;
CREATE TABLE IF NOT EXISTS `privilegios` (
  `IDusuario` int(10) UNSIGNED DEFAULT NULL,
  `status` enum('S','N') DEFAULT 'S',
  `modBuscar` enum('S','N') DEFAULT NULL,
  `modInOut` enum('S','N') DEFAULT NULL,
  `modConsulta` enum('S','N') DEFAULT NULL,
  `modPerCod` enum('S','N') DEFAULT NULL,
  `modAlterUsuarios` enum('S','N') DEFAULT 'N',
  KEY `FK_usuarios` (`IDusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `privilegios`
--

INSERT INTO `privilegios` (`IDusuario`, `status`, `modBuscar`, `modInOut`, `modConsulta`, `modPerCod`, `modAlterUsuarios`) VALUES
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
(17, 'S', 'S', 'S', 'S', 'N', 'N');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE IF NOT EXISTS `usuarios` (
  `IDusuario` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nickname` varchar(35) DEFAULT NULL,
  `nombre` varchar(35) DEFAULT NULL,
  `paterno` varchar(35) DEFAULT NULL,
  `materno` varchar(35) DEFAULT NULL,
  `cargo` varchar(35) DEFAULT NULL,
  `boleta` varchar(35) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IDusuario`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`IDusuario`, `nickname`, `nombre`, `paterno`, `materno`, `cargo`, `boleta`, `password`) VALUES
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

--
-- Filtros para la tabla `movimiento`
--
ALTER TABLE `movimiento`
  ADD CONSTRAINT `FK_IDmercancia` FOREIGN KEY (`IDmercancia`) REFERENCES `mercancia` (`IDmercancia`),
  ADD CONSTRAINT `FK_IDusuario` FOREIGN KEY (`IDusuario`) REFERENCES `usuarios` (`IDusuario`);

--
-- Filtros para la tabla `privilegios`
--
ALTER TABLE `privilegios`
  ADD CONSTRAINT `FK_privilegios` FOREIGN KEY (`IDusuario`) REFERENCES `usuarios` (`IDusuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
