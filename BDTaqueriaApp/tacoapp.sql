-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 18-05-2022 a las 05:20:07
-- Versión del servidor: 10.4.21-MariaDB
-- Versión de PHP: 7.3.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tacoapp`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `usuario` varchar(50) NOT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `contrasena` varchar(50) DEFAULT NULL,
  `telefono` int(11) DEFAULT NULL,
  `localidad` varchar(50) DEFAULT NULL,
  `direccion` varchar(50) DEFAULT NULL,
  `fotoRef` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`usuario`, `nombre`, `contrasena`, `telefono`, `localidad`, `direccion`, `fotoRef`) VALUES
('@jos', 'jose', '1234', 1234567890, NULL, NULL, NULL),
('@jos1', 'Jose', '1234', 1234567890, NULL, NULL, NULL),
('@pa1', 'Pablo', '1234', 2147483647, 'Tepanacitla', '21 de marzo', 'foto.png'),
('are1', 'are', '123', 1234567890, 'tepanacitla.', '21 fe marzo', 'foto.png'),
('blo@', 'panblo', '123', 1234567890, 'Tepanacitla', '21 de marzo', 'foto.png'),
('mar@', 'mario', '123', 1234567890, 'Tepanacitla', '21 de marzo', 'foto.png'),
('mar@1', 'mario', '123', 1234567890, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos`
--

CREATE TABLE `pedidos` (
  `id` varchar(50) NOT NULL,
  `usuario` varchar(50) NOT NULL,
  `descripcion` varchar(50) NOT NULL,
  `total` varchar(20) NOT NULL,
  `fecha` datetime(6) NOT NULL,
  `estado` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `pedidos`
--

INSERT INTO `pedidos` (`id`, `usuario`, `descripcion`, `total`, `fecha`, `estado`) VALUES
('2', 'are3', '1 kg de longanizada, 1 kg de campechana, ', '230', '2022-05-16 07:14:58.000000', 'enviado'),
('PE1', 'are1', '1 kg de asada, 1 cemitas de asada, ', '135', '2022-05-16 14:00:19.000000', 'recibido'),
('PE105', 'blo@', '1 kg de longanizada, 1 tacos campechanos, ', '104', '2022-05-17 21:58:11.000000', 'enviado'),
('PE124', 'are1', '4 kg de asada, 1 tacos campechanos, ', '454', '2022-05-17 10:21:14.000000', 'enviado'),
('PE148', 'are1', '4 kg de longanizada, ', '360', '2022-05-16 17:10:41.000000', 'enviado'),
('PE16', 'mar@', '5 kg de asada, ', '550', '2022-05-17 21:32:08.000000', 'enviado'),
('PE44', 'are1', '1 kg de asada, ', '110', '2022-05-16 14:24:45.000000', 'enviado'),
('PE58', 'are1', '2 kg de longanizada, ', '180', '2022-05-17 14:36:56.000000', 'enviado'),
('PE59', 'mar@', '1 kg de asada, ', '110', '2022-05-17 21:23:20.000000', 'enviado'),
('PE76', 'are1', '1 kg de asada, 2 kg de longanizada, ', '290', '2022-05-16 14:25:30.000000', 'enviado'),
('PE89', '@pa1', '1 kg de longanizada, 7 tacos de asada, ', '188', '2022-05-17 22:04:57.000000', 'recibido'),
('PE9', 'mar@', '1 tacos de asada, 1 cemitas de longaniza, ', '39', '2022-05-17 21:20:11.000000', 'enviado');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`usuario`);

--
-- Indices de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
