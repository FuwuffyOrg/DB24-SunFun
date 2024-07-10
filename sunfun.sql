-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 09, 2024 at 01:39 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+01:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sunfun`
--
CREATE DATABASE IF NOT EXISTS `sunfun` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `sunfun`;

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `email` varchar(256) NOT NULL COMMENT 'Email relativa all''account.',
  `password` varchar(24) NOT NULL COMMENT 'Password dell''account.',
  `tipologia` enum('Parente','Partecipante','Educatore','Volontario') NOT NULL COMMENT 'Tipo di account, ridondanza, per capire la tipologia di persona che ha l''account.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `attivita`
--

DROP TABLE IF EXISTS `attivita`;
CREATE TABLE `attivita` (
  `nome` varchar(50) NOT NULL COMMENT 'Nome dell''attivitá da svolgere.',
  `descrizione` varchar(10000) DEFAULT NULL COMMENT 'Descrizione dell''attivitá'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
CREATE TABLE `categoria` (
  `nome` varchar(16) NOT NULL COMMENT 'Il nome della categoria del forum.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `dieta`
--

DROP TABLE IF EXISTS `dieta`;
CREATE TABLE `dieta` (
  `nome` varchar(30) NOT NULL COMMENT 'Il nome della dieta.',
  `descrizione` varchar(255) DEFAULT NULL COMMENT 'Descrizione associata alla dieta.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `discussione`
--

DROP TABLE IF EXISTS `discussione`;
CREATE TABLE `discussione` (
  `num_discussione` int(11) NOT NULL COMMENT 'Numero della discussione in ordine di apretura.',
  `titolo` varchar(50) NOT NULL COMMENT 'Titolo della discussione.',
  `descrizione` varchar(10000) NOT NULL COMMENT 'Dscrizione della discussione (testo).',
  `fk_categoria` varchar(16) DEFAULT NULL COMMENT 'Chiave esterna della categoria correlata.',
  `fk_account` varchar(256) DEFAULT NULL COMMENT 'Account che ha iniziato la discussione.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `educatore`
--

DROP TABLE IF EXISTS `educatore`;
CREATE TABLE `educatore` (
  `codice_fiscale` varchar(16) NOT NULL COMMENT 'Il codice fiscale dell''educatore.',
  `nome` varchar(36) NOT NULL COMMENT 'Il nome dell''educatore.',
  `cognome` varchar(36) NOT NULL COMMENT 'Il cognome dell''educatore.',
  `cellulare` varchar(10) NOT NULL COMMENT 'Il numero di telefono dell''educatore.',
  `fk_gruppo` varchar(20) NOT NULL COMMENT 'Chiave esterna del gruppo che verrá educato dall''educatore.',
  `fk_account` varchar(256) NOT NULL COMMENT 'Chiave esterna dell''account dell''educatore.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `giornata`
--

DROP TABLE IF EXISTS `giornata`;
CREATE TABLE `giornata` (
  `data` date NOT NULL COMMENT 'La data della giornata.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `gruppo`
--

DROP TABLE IF EXISTS `gruppo`;
CREATE TABLE `gruppo` (
  `nome` varchar(20) NOT NULL COMMENT 'Nome del gruppo.',
  `eta_min` tinyint(4) NOT NULL COMMENT 'Etá minima del range di etá del gruppo.',
  `eta_max` tinyint(4) NOT NULL COMMENT 'Etá massima del range di etá del gruppo.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `intolleranza`
--

DROP TABLE IF EXISTS `intolleranza`;
CREATE TABLE `intolleranza` (
  `fk_partecipante` varchar(16) NOT NULL COMMENT 'Chiave esterna del partecipante che ha una intolleranza.',
  `fk_sostanza` varchar(30) NOT NULL COMMENT 'Chiave esterna della sostanza alla quale il partecipante é intollerante.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `modalita`
--

DROP TABLE IF EXISTS `modalita`;
CREATE TABLE `modalita` (
  `tempo_pieno` tinyint(1) NOT NULL COMMENT 'Booleano per controllare che il partecipante si é iscritto a tempo pieno.',
  `pasti` tinyint(1) NOT NULL COMMENT 'Booleano per controllare che il partecipante non ha il pranzo al sacco.',
  `fk_data_inizio` date NOT NULL COMMENT 'Chiave esterna della data di inizio del periodo di iscrizione.',
  `fk_data_fine` date NOT NULL COMMENT 'Chiave esterna della data di fine del periodo di iscrizione.',
  `fk_partecipante` varchar(16) NOT NULL COMMENT 'Chiave esterna del partecipante che si é iscritto.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `parente`
--

DROP TABLE IF EXISTS `parente`;
CREATE TABLE `parente` (
  `codice_fiscale` varchar(16) NOT NULL COMMENT 'Codice fiscale del parente.',
  `fk_account` varchar(256) NOT NULL COMMENT 'Account del parente.',
  `nome` varchar(36) NOT NULL COMMENT 'Nome del parente.',
  `cognome` varchar(36) NOT NULL COMMENT 'Cognome del parente.',
  `cellulare` varchar(10) NOT NULL COMMENT 'Numero telefonico del parente.',
  `grado_di_parentela` enum('Padre','Madre','Nonno','Nonna','Zio','Zia','Fratello','Sorella') NOT NULL COMMENT 'Grado di parentela del parente.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `partecipante`
--

DROP TABLE IF EXISTS `partecipante`;
CREATE TABLE `partecipante` (
  `codice_fiscale` varchar(16) NOT NULL COMMENT 'Codice fiscale del partecipante.',
  `fk_account` varchar(256) NOT NULL COMMENT 'Account del partecipante.',
  `fk_dieta` varchar(30) NOT NULL COMMENT 'Chiave esterna della dieta relativa al partecipante.',
  `fk_gruppo` varchar(20) NOT NULL COMMENT 'Chiave esterna del gruppo in cui é il partecipante.',
  `nome` varchar(36) NOT NULL COMMENT 'Il nome del partecipante.',
  `cognome` varchar(36) NOT NULL COMMENT 'Il cognome del partecipante.',
  `data_di_nascita` date NOT NULL COMMENT 'La data di nascita.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `periodo`
--

DROP TABLE IF EXISTS `periodo`;
CREATE TABLE `periodo` (
  `data_inizio` date NOT NULL COMMENT 'Data di inizio del periodo.',
  `data_fine` date NOT NULL COMMENT 'Data di fine del periodo.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `presenza_educatore`
--

DROP TABLE IF EXISTS `presenza_educatore`;
CREATE TABLE `presenza_educatore` (
  `presenza` tinyint(1) NOT NULL COMMENT 'Booleano per controllare che l''educatore sia presente.',
  `fk_educatore` varchar(16) NOT NULL COMMENT 'Chiave esterna dell''educatore per la presenza.',
  `fk_giornata` date NOT NULL COMMENT 'Chiave esterna della giornata durante la quale l''educatore é presente.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `presenza_partecipante`
--

DROP TABLE IF EXISTS `presenza_partecipante`;
CREATE TABLE `presenza_partecipante` (
  `entrata` tinyint(1) NOT NULL COMMENT 'Booleano per controllare che un partecipante sia entrato nel centro estivo (per l''appello).',
  `uscita` tinyint(1) NOT NULL COMMENT 'Booleano per controllare che un partecipante sia uscito dal centro estivo (per l''appello).',
  `fk_partecipante` varchar(16) NOT NULL COMMENT 'Chiave esterna del partecipante per controllare la presenza.',
  `fk_giornata` date NOT NULL COMMENT 'Chiave esterna della giornata durante la quale si vuole controllare la presenza del partecipante.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `recensione`
--

DROP TABLE IF EXISTS `recensione`;
CREATE TABLE `recensione` (
  `voto` tinyint(4) NOT NULL COMMENT 'Voto della recensione.',
  `descrizione` varchar(10000) DEFAULT NULL COMMENT 'Descrizione della recensione dell''attivitá.',
  `fk_attivita` varchar(50) NOT NULL COMMENT 'Chiave esterna dell''attivitá da recensire.',
  `fk_account` varchar(16) NOT NULL COMMENT 'Chiave esterna dell''account che scrive la recensione.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `risposta`
--

DROP TABLE IF EXISTS `risposta`;
CREATE TABLE `risposta` (
  `num_risposta` int(11) NOT NULL COMMENT 'Numero della risposta di una discussione.',
  `testo` varchar(10000) NOT NULL COMMENT 'Testo della risposta di una discussione.',
  `fk_discussione` int(11) NOT NULL COMMENT 'Chiave esterna della discussione riguardante la risposta.',
  `fk_account` varchar(256) NOT NULL COMMENT 'Chiave esterna dell''account che scrive la risposta.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ritiro`
--

DROP TABLE IF EXISTS `ritiro`;
CREATE TABLE `ritiro` (
  `fk_parente` varchar(16) NOT NULL COMMENT 'Chiave esterna del parente che puó ritirare un partecipante.',
  `fk_partecipante` varchar(16) NOT NULL COMMENT 'Chiave esterna del partecipante che potrá essere ritirato dal parente.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sostanza`
--

DROP TABLE IF EXISTS `sostanza`;
CREATE TABLE `sostanza` (
  `nome` varchar(30) NOT NULL COMMENT 'Il nome della sostanza per le intolleranze.',
  `descrizione` varchar(255) DEFAULT NULL COMMENT 'Descrizione della sostanza.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `svolgimento`
--

DROP TABLE IF EXISTS `svolgimento`;
CREATE TABLE `svolgimento` (
  `ora_inizio` time NOT NULL COMMENT 'Orario di inizio dell''attivitá.',
  `ora_fine` time NOT NULL COMMENT 'Orario di fine dell''attivitá.',
  `fk_attivita` varchar(50) NOT NULL COMMENT 'Chiave esterna dell''attivitá da svolgere.',
  `fk_gruppo` varchar(20) NOT NULL COMMENT 'Chiave esterna del gruppo che potrá svolgere l''attivitá.',
  `fk_giornata` date NOT NULL COMMENT 'Chiave esterna della giornata durante la quale verrá svolta l''attivitá.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `volontario`
--

DROP TABLE IF EXISTS `volontario`;
CREATE TABLE `volontario` (
  `codice_fiscale` varchar(16) NOT NULL COMMENT 'Codice fiscale del volontario.',
  `fk_account` varchar(256) NOT NULL COMMENT 'Account del volontario.',
  `nome` varchar(36) NOT NULL COMMENT ' Il nome del volontario.',
  `cognome` varchar(36) NOT NULL COMMENT 'Il cognome del volontario.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`email`);

--
-- Indexes for table `attivita`
--
ALTER TABLE `attivita`
  ADD PRIMARY KEY (`nome`);

--
-- Indexes for table `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`nome`);

--
-- Indexes for table `dieta`
--
ALTER TABLE `dieta`
  ADD PRIMARY KEY (`nome`);

--
-- Indexes for table `discussione`
--
ALTER TABLE `discussione`
  ADD PRIMARY KEY (`num_discussione`),
  ADD KEY `FK Categoria Discussione` (`fk_categoria`),
  ADD KEY `FK Account Discussione` (`fk_account`);

--
-- Indexes for table `educatore`
--
ALTER TABLE `educatore`
  ADD PRIMARY KEY (`codice_fiscale`),
  ADD KEY `FK Account Educatore` (`fk_account`),
  ADD KEY `FK Gruppo Educatore` (`fk_gruppo`);

--
-- Indexes for table `giornata`
--
ALTER TABLE `giornata`
  ADD PRIMARY KEY (`data`);

--
-- Indexes for table `gruppo`
--
ALTER TABLE `gruppo`
  ADD PRIMARY KEY (`nome`);

--
-- Indexes for table `intolleranza`
--
ALTER TABLE `intolleranza`
  ADD PRIMARY KEY (`fk_partecipante`,`fk_sostanza`),
  ADD KEY `FK Sostanza Intolleranza` (`fk_sostanza`),
  ADD KEY `FK Partecipante Intolleranza` (`fk_partecipante`) USING BTREE;

--
-- Indexes for table `modalita`
--
ALTER TABLE `modalita`
  ADD PRIMARY KEY (`fk_data_inizio`,`fk_data_fine`,`fk_partecipante`),
  ADD KEY `FK Partecipante Modalita` (`fk_partecipante`),
  ADD KEY `FK Periodo Modalita` (`fk_data_inizio`,`fk_data_fine`);

--
-- Indexes for table `parente`
--
ALTER TABLE `parente`
  ADD PRIMARY KEY (`codice_fiscale`),
  ADD KEY `FK Account Parente` (`fk_account`);

--
-- Indexes for table `partecipante`
--
ALTER TABLE `partecipante`
  ADD PRIMARY KEY (`codice_fiscale`),
  ADD KEY `FK Account Partecipante` (`fk_account`) USING BTREE,
  ADD KEY `FK Dieta` (`fk_dieta`),
  ADD KEY `FK Gruppo` (`fk_gruppo`);

--
-- Indexes for table `periodo`
--
ALTER TABLE `periodo`
  ADD PRIMARY KEY (`data_inizio`,`data_fine`);

--
-- Indexes for table `presenza_educatore`
--
ALTER TABLE `presenza_educatore`
  ADD PRIMARY KEY (`fk_educatore`,`fk_giornata`),
  ADD KEY `FK Giornata Presenza Educatore` (`fk_giornata`),
  ADD KEY `FK Educatore Presenza Educatore` (`fk_educatore`);

--
-- Indexes for table `presenza_partecipante`
--
ALTER TABLE `presenza_partecipante`
  ADD PRIMARY KEY (`fk_partecipante`,`fk_giornata`),
  ADD KEY `FK Giornata Presenza Partecipante` (`fk_giornata`),
  ADD KEY `FK Partecipante Presenza Partecipante` (`fk_partecipante`);

--
-- Indexes for table `recensione`
--
ALTER TABLE `recensione`
  ADD PRIMARY KEY (`fk_attivita`,`fk_account`),
  ADD KEY `FK Account Recensione` (`fk_account`),
  ADD KEY `FK Attivita Recensione` (`fk_attivita`) USING BTREE;

--
-- Indexes for table `risposta`
--
ALTER TABLE `risposta`
  ADD PRIMARY KEY (`num_risposta`,`fk_discussione`) USING BTREE,
  ADD KEY `FK Discussione Risposta` (`fk_discussione`),
  ADD KEY `FK Account Risposta` (`fk_account`);

--
-- Indexes for table `ritiro`
--
ALTER TABLE `ritiro`
  ADD PRIMARY KEY (`fk_parente`,`fk_partecipante`),
  ADD KEY `FK Partecipante Ritiro` (`fk_partecipante`),
  ADD KEY `FK Parente Ritiro` (`fk_parente`) USING BTREE;

--
-- Indexes for table `sostanza`
--
ALTER TABLE `sostanza`
  ADD PRIMARY KEY (`nome`);

--
-- Indexes for table `svolgimento`
--
ALTER TABLE `svolgimento`
  ADD PRIMARY KEY (`fk_attivita`,`fk_gruppo`,`fk_giornata`),
  ADD KEY `FK Gruppo Svolgimento` (`fk_gruppo`),
  ADD KEY `FK Giornata Svolgimento` (`fk_giornata`),
  ADD KEY `FK Attivita Svolgimento` (`fk_attivita`);

--
-- Indexes for table `volontario`
--
ALTER TABLE `volontario`
  ADD PRIMARY KEY (`codice_fiscale`),
  ADD KEY `FK Account Volontario` (`fk_account`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `discussione`
--
ALTER TABLE `discussione`
  MODIFY `num_discussione` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Numero della discussione in ordine di apretura.';

--
-- AUTO_INCREMENT for table `risposta`
--
ALTER TABLE `risposta`
  MODIFY `num_risposta` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Numero della risposta di una discussione.';

--
-- Constraints for dumped tables
--

--
-- Constraints for table `discussione`
--
ALTER TABLE `discussione`
  ADD CONSTRAINT `FK Account Discussione` FOREIGN KEY (`fk_account`) REFERENCES `account` (`email`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Categoria Discussione` FOREIGN KEY (`fk_categoria`) REFERENCES `categoria` (`nome`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `educatore`
--
ALTER TABLE `educatore`
  ADD CONSTRAINT `FK Account Educatore` FOREIGN KEY (`fk_account`) REFERENCES `account` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Gruppo Educatore` FOREIGN KEY (`fk_gruppo`) REFERENCES `gruppo` (`nome`) ON UPDATE CASCADE;

--
-- Constraints for table `intolleranza`
--
ALTER TABLE `intolleranza`
  ADD CONSTRAINT `FK Partecipante Intolleranza` FOREIGN KEY (`fk_partecipante`) REFERENCES `partecipante` (`codice_fiscale`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Sostanza Intolleranza` FOREIGN KEY (`fk_sostanza`) REFERENCES `sostanza` (`nome`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `modalita`
--
ALTER TABLE `modalita`
  ADD CONSTRAINT `FK Partecipante Modalita` FOREIGN KEY (`fk_partecipante`) REFERENCES `partecipante` (`codice_fiscale`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Periodo Modalita` FOREIGN KEY (`fk_data_inizio`,`fk_data_fine`) REFERENCES `periodo` (`data_inizio`, `data_fine`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `parente`
--
ALTER TABLE `parente`
  ADD CONSTRAINT `FK Account Parente` FOREIGN KEY (`fk_account`) REFERENCES `account` (`email`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `partecipante`
--
ALTER TABLE `partecipante`
  ADD CONSTRAINT `FK Account` FOREIGN KEY (`fk_account`) REFERENCES `account` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Dieta` FOREIGN KEY (`fk_dieta`) REFERENCES `dieta` (`nome`) ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Gruppo` FOREIGN KEY (`fk_gruppo`) REFERENCES `gruppo` (`nome`) ON UPDATE CASCADE;

--
-- Constraints for table `presenza_educatore`
--
ALTER TABLE `presenza_educatore`
  ADD CONSTRAINT `FK Educatore Presenza Educatore` FOREIGN KEY (`fk_educatore`) REFERENCES `educatore` (`codice_fiscale`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Giornata Presenza Educatore` FOREIGN KEY (`fk_giornata`) REFERENCES `giornata` (`data`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `presenza_partecipante`
--
ALTER TABLE `presenza_partecipante`
  ADD CONSTRAINT `FK Giornata Presenza Partecipante` FOREIGN KEY (`fk_giornata`) REFERENCES `giornata` (`data`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Partecipante Presenza Partecipante` FOREIGN KEY (`fk_partecipante`) REFERENCES `partecipante` (`codice_fiscale`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `recensione`
--
ALTER TABLE `recensione`
  ADD CONSTRAINT `FK Account Recensione` FOREIGN KEY (`fk_account`) REFERENCES `account` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Attivita Recensione` FOREIGN KEY (`fk_attivita`) REFERENCES `attivita` (`nome`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `risposta`
--
ALTER TABLE `risposta`
  ADD CONSTRAINT `FK Account Risposta` FOREIGN KEY (`fk_account`) REFERENCES `account` (`email`) ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Discussione Risposta` FOREIGN KEY (`fk_discussione`) REFERENCES `discussione` (`num_discussione`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `ritiro`
--
ALTER TABLE `ritiro`
  ADD CONSTRAINT `FK Parente Ritiro` FOREIGN KEY (`fk_parente`) REFERENCES `parente` (`codice_fiscale`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Partecipante Ritiro` FOREIGN KEY (`fk_partecipante`) REFERENCES `partecipante` (`codice_fiscale`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `svolgimento`
--
ALTER TABLE `svolgimento`
  ADD CONSTRAINT `FK Attivita Svolgimento` FOREIGN KEY (`fk_attivita`) REFERENCES `attivita` (`nome`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Giornata Svolgimento` FOREIGN KEY (`fk_giornata`) REFERENCES `giornata` (`data`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Gruppo Svolgimento` FOREIGN KEY (`fk_gruppo`) REFERENCES `gruppo` (`nome`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `volontario`
--
ALTER TABLE `volontario`
  ADD CONSTRAINT `FK Account Volontario` FOREIGN KEY (`fk_account`) REFERENCES `account` (`email`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
