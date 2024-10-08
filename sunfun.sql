SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+01:00";

CREATE DATABASE IF NOT EXISTS `sunfun` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `sunfun`;

DROP TABLE IF EXISTS `account`;
CREATE TABLE IF NOT EXISTS `account` (
  `email` varchar(256) NOT NULL COMMENT 'Email relativa all''account.',
  `password` varchar(42) NOT NULL COMMENT 'Password dell''account.',
  `tipologia` enum('Parente','Partecipante','Educatore','Volontario') NOT NULL COMMENT 'Tipo di account, ridondanza, per capire la tipologia di persona che ha l''account.',
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `account` (`email`, `password`, `tipologia`) VALUES
('admin@admin.com', '*4ACFE3202A5FF5CF467898FC58AAB1D615029441', 'Educatore');
DROP VIEW IF EXISTS `account_data`;
CREATE TABLE IF NOT EXISTS `account_data` (
`email` varchar(256)
,`password` varchar(42)
,`tipologia` enum('Parente','Partecipante','Educatore','Volontario')
,`nome` varchar(36)
,`cognome` varchar(36)
,`codice_fiscale` varchar(16)
);

DROP TABLE IF EXISTS `allergene`;
CREATE TABLE IF NOT EXISTS `allergene` (
  `nome` varchar(30) NOT NULL COMMENT 'Il nome dell''allergene per le intolleranze.',
  `descrizione` varchar(255) DEFAULT NULL COMMENT 'Descrizione dell''allergene.',
  PRIMARY KEY (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `allergene` (`nome`, `descrizione`) VALUES
('Glutine', 'Un\'intolleranza che riguarda tutto ció che contiene glutine.'),
('Latticini', 'Un\'intolleranza che riguarda tutto ció che contiene i latticini.'),
('Uovo', 'Intolleranza alle uova.');

DROP TABLE IF EXISTS `attivita`;
CREATE TABLE IF NOT EXISTS `attivita` (
  `nome` varchar(50) NOT NULL COMMENT 'Nome dell''attivitá da svolgere.',
  `descrizione` varchar(10000) DEFAULT NULL COMMENT 'Descrizione dell''attivitá',
  PRIMARY KEY (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `attivita` (`nome`, `descrizione`) VALUES
('Bandiera genovese', 'Viene diviso il campo in due metà uguali e nel fondo di ciascuna metà viene posizionata \nuna bandiera (o simili). I bambini vengono divisi in due squadre, le quali si posizioneranno\nciascuna in una metà. Lo scopo del gioco consiste nel riuscire attraversare il campo avversario, \ncon l’aiuto dei compagni, senza farsi toccare dai bambini per prendere la bandiera e riportarla \nnel proprio campo. Se il giocatore in campo nemico viene toccato dall’avversario si dovrà\nimmobilizzare fino a quando un suo compagno di squadra non andrà a liberarlo.'),
('Battle royale', 'Si prepara il campo di gioco (l’ideale è una vasta area libera da ostacoli come il prato dietro il pallone da calcio) \ne si prendono le peteche (più sono, meglio è). Vengono distribuite 5-10 peteche a bambino e un pennarello. \nI ragazzi devono posizionarsi all’interno del campo il più distanziati possibile. Al via inizieranno a correre e cercheranno di colpirsi con le peteche. \nOgni volta che si viene colpiti da una peteca si fa un segno sul braccio con il pennarello. Quando ci si segna il braccio bisogna \nmettersi in ginocchio per terra; chi è in ginocchio a terra è immune dalle peteche. Una partita dura circa 20-30 minuti, poi si chiama la fine. \nIl ragazzo/a che ha meno segni sul braccio vince.\n'),
('Stratego', 'Si prepara il campo di gioco (l’ideale è una vasta area libera da ostacoli come il prato dietro il pallone da calcio) \ne si prendono le carte di stratego. In ogni carta c’è un’immagine e un numero: l’immagine ha una funzione puramente estetica, \nil numero rappresenta la forza del giocatore. I ragazzi vengono divisi in due squadre (se si ha il numero giusto di giocatori è possibile giocare anche con 3 o 4 squadre) \ne a ognuno viene distribuita una carta. Le squadre vengono allontanate e posizionate alle estremità del campo di gioco. Al via i ragazzi iniziano a correre e cercano di prendersi; \nogni volta che un giocatore tocca / prende un avversario entrambi vanno alla postazione degli educatori e consegnano le loro carte. Gli educatori controllano i valori e assegnano \nal giocatore con il valore maggiore la differenza di punti tra le due carte, SENZA RIVELARE AI RAGAZZI CHI HA GUADAGNATO PUNTI. Si riconsegnano le carte ai ragazzi che possono \ntornare a giocare. L’unica eccezione c’è quando il giocatore dal livello più basso (1) prende il giocatore dal livello più alto (dipende dal numero di giocatori). \nIl punteggio di questo scontro viene illustrato nella tabella sotto. Una partita dura circa 30 minuti. Finito il tempo vince la squadra con il punteggio maggiore.');

DROP TABLE IF EXISTS `categoria`;
CREATE TABLE IF NOT EXISTS `categoria` (
  `nome` varchar(16) NOT NULL COMMENT 'Il nome della categoria del forum.',
  PRIMARY KEY (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `categoria` (`nome`) VALUES
('Attivita'),
('Generic'),
('Novita'),
('Pasti');

DROP TABLE IF EXISTS `dieta`;
CREATE TABLE IF NOT EXISTS `dieta` (
  `nome` varchar(30) NOT NULL COMMENT 'Il nome della dieta.',
  `descrizione` varchar(255) DEFAULT NULL COMMENT 'Descrizione associata alla dieta.',
  PRIMARY KEY (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `dieta` (`nome`, `descrizione`) VALUES
('No Carne Suina', 'Una dieta priva di carne suina.'),
('No Pesce', 'Una dieta in cui non si mangia carne di pesce.'),
('Vegana', 'Una dieta priva di risorse provenienti dal regno animale.'),
('Vegetariana', 'Una dieta priva di carne e pesce.');

DROP TABLE IF EXISTS `discussione`;
CREATE TABLE IF NOT EXISTS `discussione` (
  `num_discussione` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Numero della discussione in ordine di apretura.',
  `titolo` varchar(50) NOT NULL COMMENT 'Titolo della discussione.',
  `descrizione` varchar(10000) NOT NULL COMMENT 'Dscrizione della discussione (testo).',
  `fk_categoria` varchar(16) DEFAULT NULL COMMENT 'Chiave esterna della categoria correlata.',
  `fk_account` varchar(256) DEFAULT NULL COMMENT 'Account che ha iniziato la discussione.',
  PRIMARY KEY (`num_discussione`),
  KEY `FK Categoria Discussione` (`fk_categoria`),
  KEY `FK Account Discussione` (`fk_account`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `educatore`;
CREATE TABLE IF NOT EXISTS `educatore` (
  `codice_fiscale` varchar(16) NOT NULL COMMENT 'Il codice fiscale dell''educatore.',
  `nome` varchar(36) NOT NULL COMMENT 'Il nome dell''educatore.',
  `cognome` varchar(36) NOT NULL COMMENT 'Il cognome dell''educatore.',
  `cellulare` varchar(10) NOT NULL COMMENT 'Il numero di telefono dell''educatore.',
  `fk_gruppo` varchar(20) DEFAULT NULL COMMENT 'Chiave esterna del gruppo che verrá educato dall''educatore.',
  `fk_account` varchar(256) NOT NULL COMMENT 'Chiave esterna dell''account dell''educatore.',
  PRIMARY KEY (`codice_fiscale`),
  KEY `FK Account Educatore` (`fk_account`),
  KEY `FK Gruppo Educatore` (`fk_gruppo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `educatore` (`codice_fiscale`, `nome`, `cognome`, `cellulare`, `fk_gruppo`, `fk_account`) VALUES
('DMNDMN73H24E506X', 'Admin', 'Adminson', '0000000000', NULL, 'admin@admin.com');

DROP TABLE IF EXISTS `giornata`;
CREATE TABLE IF NOT EXISTS `giornata` (
  `data` date NOT NULL COMMENT 'La data della giornata.',
  `fk_periodo_inizio` date NOT NULL COMMENT 'Chiave esterna della data di inizio del periodo associato.',
  `fk_periodo_fine` date NOT NULL COMMENT 'Chiave esterna della data di fine del periodo associato.',
  PRIMARY KEY (`data`,`fk_periodo_inizio`,`fk_periodo_fine`) USING BTREE,
  KEY `FK Periodo Giornata` (`fk_periodo_inizio`,`fk_periodo_fine`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `giornata` (`data`, `fk_periodo_inizio`, `fk_periodo_fine`) VALUES
('2024-06-10', '2024-06-10', '2024-06-21'),
('2024-06-11', '2024-06-10', '2024-06-21'),
('2024-06-12', '2024-06-10', '2024-06-21'),
('2024-06-13', '2024-06-10', '2024-06-21'),
('2024-06-14', '2024-06-10', '2024-06-21'),
('2024-06-15', '2024-06-10', '2024-06-21'),
('2024-06-16', '2024-06-10', '2024-06-21'),
('2024-06-17', '2024-06-10', '2024-06-21'),
('2024-06-18', '2024-06-10', '2024-06-21'),
('2024-06-19', '2024-06-10', '2024-06-21'),
('2024-06-20', '2024-06-10', '2024-06-21'),
('2024-06-21', '2024-06-10', '2024-06-21'),
('2024-06-24', '2024-06-24', '2024-07-05'),
('2024-06-25', '2024-06-24', '2024-07-05'),
('2024-06-26', '2024-06-24', '2024-07-05'),
('2024-06-27', '2024-06-24', '2024-07-05'),
('2024-06-28', '2024-06-24', '2024-07-05'),
('2024-06-29', '2024-06-24', '2024-07-05'),
('2024-06-30', '2024-06-24', '2024-07-05'),
('2024-07-01', '2024-06-24', '2024-07-05'),
('2024-07-02', '2024-06-24', '2024-07-05'),
('2024-07-03', '2024-06-24', '2024-07-05'),
('2024-07-04', '2024-06-24', '2024-07-05'),
('2024-07-05', '2024-06-24', '2024-07-05'),
('2024-07-08', '2024-07-08', '2024-07-19'),
('2024-07-09', '2024-07-08', '2024-07-19'),
('2024-07-10', '2024-07-08', '2024-07-19'),
('2024-07-11', '2024-07-08', '2024-07-19'),
('2024-07-12', '2024-07-08', '2024-07-19'),
('2024-07-13', '2024-07-08', '2024-07-19'),
('2024-07-14', '2024-07-08', '2024-07-19'),
('2024-07-15', '2024-07-08', '2024-07-19'),
('2024-07-16', '2024-07-08', '2024-07-19'),
('2024-07-17', '2024-07-08', '2024-07-19'),
('2024-07-18', '2024-07-08', '2024-07-19'),
('2024-07-19', '2024-07-08', '2024-07-19'),
('2024-07-22', '2024-07-22', '2024-08-02'),
('2024-07-23', '2024-07-22', '2024-08-02'),
('2024-07-24', '2024-07-22', '2024-08-02'),
('2024-07-25', '2024-07-22', '2024-08-02'),
('2024-07-26', '2024-07-22', '2024-08-02'),
('2024-07-27', '2024-07-22', '2024-08-02'),
('2024-07-28', '2024-07-22', '2024-08-02'),
('2024-07-29', '2024-07-22', '2024-08-02'),
('2024-07-30', '2024-07-22', '2024-08-02'),
('2024-07-31', '2024-07-22', '2024-08-02'),
('2024-08-01', '2024-07-22', '2024-08-02'),
('2024-08-02', '2024-07-22', '2024-08-02'),
('2024-08-05', '2024-08-05', '2024-08-16'),
('2024-08-06', '2024-08-05', '2024-08-16'),
('2024-08-07', '2024-08-05', '2024-08-16'),
('2024-08-08', '2024-08-05', '2024-08-16'),
('2024-08-09', '2024-08-05', '2024-08-16'),
('2024-08-10', '2024-08-05', '2024-08-16'),
('2024-08-11', '2024-08-05', '2024-08-16'),
('2024-08-12', '2024-08-05', '2024-08-16'),
('2024-08-13', '2024-08-05', '2024-08-16'),
('2024-08-14', '2024-08-05', '2024-08-16'),
('2024-08-15', '2024-08-05', '2024-08-16'),
('2024-08-16', '2024-08-05', '2024-08-16'),
('2024-08-19', '2024-08-19', '2024-08-30'),
('2024-08-20', '2024-08-19', '2024-08-30'),
('2024-08-21', '2024-08-19', '2024-08-30'),
('2024-08-22', '2024-08-19', '2024-08-30'),
('2024-08-23', '2024-08-19', '2024-08-30'),
('2024-08-24', '2024-08-19', '2024-08-30'),
('2024-08-25', '2024-08-19', '2024-08-30'),
('2024-08-26', '2024-08-19', '2024-08-30'),
('2024-08-27', '2024-08-19', '2024-08-30'),
('2024-08-28', '2024-08-19', '2024-08-30'),
('2024-08-29', '2024-08-19', '2024-08-30'),
('2024-08-30', '2024-08-19', '2024-08-30'),
('2024-09-02', '2024-09-02', '2024-09-13'),
('2024-09-03', '2024-09-02', '2024-09-13'),
('2024-09-04', '2024-09-02', '2024-09-13'),
('2024-09-05', '2024-09-02', '2024-09-13'),
('2024-09-06', '2024-09-02', '2024-09-13'),
('2024-09-07', '2024-09-02', '2024-09-13'),
('2024-09-08', '2024-09-02', '2024-09-13'),
('2024-09-09', '2024-09-02', '2024-09-13'),
('2024-09-10', '2024-09-02', '2024-09-13'),
('2024-09-11', '2024-09-02', '2024-09-13'),
('2024-09-12', '2024-09-02', '2024-09-13'),
('2024-09-13', '2024-09-02', '2024-09-13');

DROP TABLE IF EXISTS `gruppo`;
CREATE TABLE IF NOT EXISTS `gruppo` (
  `nome` varchar(20) NOT NULL COMMENT 'Nome del gruppo.',
  `eta_min` tinyint(4) NOT NULL COMMENT 'Etá minima del range di etá del gruppo.',
  `eta_max` tinyint(4) NOT NULL COMMENT 'Etá massima del range di etá del gruppo.',
  PRIMARY KEY (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `gruppo` (`nome`, `eta_min`, `eta_max`) VALUES
('Arancia', 8, 10),
('Cielo', 5, 6),
('Ciliegia', 10, 11),
('Nuvola', 7, 8);

DROP TABLE IF EXISTS `intolleranza`;
CREATE TABLE IF NOT EXISTS `intolleranza` (
  `fk_partecipante` varchar(16) NOT NULL COMMENT 'Chiave esterna del partecipante che ha una intolleranza.',
  `fk_allergene` varchar(30) NOT NULL COMMENT 'Chiave esterna dell''allergene alla quale il partecipante é intollerante.',
  PRIMARY KEY (`fk_partecipante`,`fk_allergene`),
  KEY `FK Allergene Intolleranza` (`fk_allergene`),
  KEY `FK Partecipante Intolleranza` (`fk_partecipante`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `modalita`;
CREATE TABLE IF NOT EXISTS `modalita` (
  `tempo_pieno` tinyint(1) NOT NULL COMMENT 'Booleano per controllare che il partecipante si é iscritto a tempo pieno.',
  `pasti` tinyint(1) NOT NULL COMMENT 'Booleano per controllare che il partecipante non ha il pranzo al sacco.',
  `fk_data_inizio` date NOT NULL COMMENT 'Chiave esterna della data di inizio del periodo di iscrizione.',
  `fk_data_fine` date NOT NULL COMMENT 'Chiave esterna della data di fine del periodo di iscrizione.',
  `fk_partecipante` varchar(16) NOT NULL COMMENT 'Chiave esterna del partecipante che si é iscritto.',
  PRIMARY KEY (`fk_data_inizio`,`fk_data_fine`,`fk_partecipante`),
  KEY `FK Partecipante Modalita` (`fk_partecipante`),
  KEY `FK Periodo Modalita` (`fk_data_inizio`,`fk_data_fine`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `parente`;
CREATE TABLE IF NOT EXISTS `parente` (
  `codice_fiscale` varchar(16) NOT NULL COMMENT 'Codice fiscale del parente.',
  `fk_account` varchar(256) NOT NULL COMMENT 'Account del parente.',
  `nome` varchar(36) NOT NULL COMMENT 'Nome del parente.',
  `cognome` varchar(36) NOT NULL COMMENT 'Cognome del parente.',
  `cellulare` varchar(10) NOT NULL COMMENT 'Numero telefonico del parente.',
  `grado_di_parentela` enum('Padre','Madre','Nonno','Nonna','Fratello','Sorella') NOT NULL COMMENT 'Grado di parentela del parente.',
  PRIMARY KEY (`codice_fiscale`),
  KEY `FK Account Parente` (`fk_account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `partecipante`;
CREATE TABLE IF NOT EXISTS `partecipante` (
  `codice_fiscale` varchar(16) NOT NULL COMMENT 'Codice fiscale del partecipante.',
  `fk_account` varchar(256) NOT NULL COMMENT 'Account del partecipante.',
  `fk_dieta` varchar(30) DEFAULT NULL COMMENT 'Chiave esterna della dieta relativa al partecipante.',
  `fk_gruppo` varchar(20) DEFAULT NULL COMMENT 'Chiave esterna del gruppo in cui é il partecipante.',
  `nome` varchar(36) NOT NULL COMMENT 'Il nome del partecipante.',
  `cognome` varchar(36) NOT NULL COMMENT 'Il cognome del partecipante.',
  `data_di_nascita` date NOT NULL COMMENT 'La data di nascita.',
  PRIMARY KEY (`codice_fiscale`),
  KEY `FK Account Partecipante` (`fk_account`) USING BTREE,
  KEY `FK Dieta` (`fk_dieta`),
  KEY `FK Gruppo` (`fk_gruppo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `periodo`;
CREATE TABLE IF NOT EXISTS `periodo` (
  `data_inizio` date NOT NULL COMMENT 'Data di inizio del periodo.',
  `data_fine` date NOT NULL COMMENT 'Data di fine del periodo.',
  PRIMARY KEY (`data_inizio`,`data_fine`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `periodo` (`data_inizio`, `data_fine`) VALUES
('2024-06-10', '2024-06-21'),
('2024-06-24', '2024-07-05'),
('2024-07-08', '2024-07-19'),
('2024-07-22', '2024-08-02'),
('2024-08-05', '2024-08-16'),
('2024-08-19', '2024-08-30'),
('2024-09-02', '2024-09-13');

DROP TABLE IF EXISTS `presenza`;
CREATE TABLE IF NOT EXISTS `presenza` (
  `entrata` tinyint(1) NOT NULL COMMENT 'Booleano per controllare che un partecipante sia entrato nel centro estivo (per l''appello).',
  `uscita` tinyint(1) NOT NULL COMMENT 'Booleano per controllare che un partecipante sia uscito dal centro estivo (per l''appello).',
  `fk_partecipante` varchar(16) NOT NULL COMMENT 'Chiave esterna del partecipante per controllare la presenza.',
  `fk_giornata` date NOT NULL COMMENT 'Chiave esterna della giornata durante la quale si vuole controllare la presenza del partecipante.',
  PRIMARY KEY (`fk_partecipante`,`fk_giornata`),
  KEY `FK Giornata Presenza Partecipante` (`fk_giornata`),
  KEY `FK Partecipante Presenza Partecipante` (`fk_partecipante`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `recensione`;
CREATE TABLE IF NOT EXISTS `recensione` (
  `voto` tinyint(4) NOT NULL COMMENT 'Voto della recensione.',
  `descrizione` varchar(10000) DEFAULT NULL COMMENT 'Descrizione della recensione dell''attivitá.',
  `fk_attivita` varchar(50) NOT NULL COMMENT 'Chiave esterna dell''attivitá da recensire.',
  `fk_account` varchar(16) NOT NULL COMMENT 'Chiave esterna dell''account che scrive la recensione.',
  PRIMARY KEY (`fk_attivita`,`fk_account`),
  KEY `FK Account Recensione` (`fk_account`),
  KEY `FK Attivita Recensione` (`fk_attivita`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `risposta`;
CREATE TABLE IF NOT EXISTS `risposta` (
  `num_risposta` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Numero della risposta di una discussione.',
  `testo` varchar(10000) NOT NULL COMMENT 'Testo della risposta di una discussione.',
  `fk_discussione` int(11) NOT NULL COMMENT 'Chiave esterna della discussione riguardante la risposta.',
  `fk_account` varchar(256) NOT NULL COMMENT 'Chiave esterna dell''account che scrive la risposta.',
  PRIMARY KEY (`num_risposta`,`fk_discussione`) USING BTREE,
  KEY `FK Discussione Risposta` (`fk_discussione`),
  KEY `FK Account Risposta` (`fk_account`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `ritiro`;
CREATE TABLE IF NOT EXISTS `ritiro` (
  `fk_parente` varchar(16) NOT NULL COMMENT 'Chiave esterna del parente che puó ritirare un partecipante.',
  `fk_partecipante` varchar(16) NOT NULL COMMENT 'Chiave esterna del partecipante che potrá essere ritirato dal parente.',
  PRIMARY KEY (`fk_parente`,`fk_partecipante`),
  KEY `FK Partecipante Ritiro` (`fk_partecipante`),
  KEY `FK Parente Ritiro` (`fk_parente`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `svolgimento`;
CREATE TABLE IF NOT EXISTS `svolgimento` (
  `ora_inizio` time NOT NULL COMMENT 'Orario di inizio dell''attivitá.',
  `ora_fine` time NOT NULL COMMENT 'Orario di fine dell''attivitá.',
  `fk_attivita` varchar(50) NOT NULL COMMENT 'Chiave esterna dell''attivitá da svolgere.',
  `fk_gruppo` varchar(20) NOT NULL COMMENT 'Chiave esterna del gruppo che potrá svolgere l''attivitá.',
  `fk_giornata` date NOT NULL COMMENT 'Chiave esterna della giornata durante la quale verrá svolta l''attivitá.',
  PRIMARY KEY (`fk_attivita`,`fk_gruppo`,`fk_giornata`),
  KEY `FK Gruppo Svolgimento` (`fk_gruppo`),
  KEY `FK Giornata Svolgimento` (`fk_giornata`),
  KEY `FK Attivita Svolgimento` (`fk_attivita`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `volontario`;
CREATE TABLE IF NOT EXISTS `volontario` (
  `codice_fiscale` varchar(16) NOT NULL COMMENT 'Codice fiscale del volontario.',
  `fk_account` varchar(256) NOT NULL COMMENT 'Account del volontario.',
  `nome` varchar(36) NOT NULL COMMENT ' Il nome del volontario.',
  `cognome` varchar(36) NOT NULL COMMENT 'Il cognome del volontario.',
  PRIMARY KEY (`codice_fiscale`),
  KEY `FK Account Volontario` (`fk_account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
DROP TABLE IF EXISTS `account_data`;

DROP VIEW IF EXISTS `account_data`;
CREATE ALGORITHM=MERGE DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `account_data`  AS SELECT `a`.`email` AS `email`, `a`.`password` AS `password`, `a`.`tipologia` AS `tipologia`, coalesce(`e`.`nome`,`pare`.`nome`,`part`.`nome`,`v`.`nome`) AS `nome`, coalesce(`e`.`cognome`,`pare`.`cognome`,`part`.`cognome`,`v`.`cognome`) AS `cognome`, coalesce(`e`.`codice_fiscale`,`pare`.`codice_fiscale`,`part`.`codice_fiscale`,`v`.`codice_fiscale`) AS `codice_fiscale` FROM ((((`account` `a` left join `educatore` `e` on(`a`.`email` = `e`.`fk_account`)) left join `parente` `pare` on(`a`.`email` = `pare`.`fk_account`)) left join `partecipante` `part` on(`a`.`email` = `part`.`fk_account`)) left join `volontario` `v` on(`a`.`email` = `v`.`fk_account`)) ;


ALTER TABLE `discussione`
  ADD CONSTRAINT `FK Account Discussione` FOREIGN KEY (`fk_account`) REFERENCES `account` (`email`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Categoria Discussione` FOREIGN KEY (`fk_categoria`) REFERENCES `categoria` (`nome`) ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE `educatore`
  ADD CONSTRAINT `FK Account Educatore` FOREIGN KEY (`fk_account`) REFERENCES `account` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Gruppo Educatore` FOREIGN KEY (`fk_gruppo`) REFERENCES `gruppo` (`nome`) ON UPDATE CASCADE;

ALTER TABLE `giornata`
  ADD CONSTRAINT `FK Periodo Giornata` FOREIGN KEY (`fk_periodo_inizio`,`fk_periodo_fine`) REFERENCES `periodo` (`data_inizio`, `data_fine`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `intolleranza`
  ADD CONSTRAINT `FK Allergene Intolleranza` FOREIGN KEY (`fk_allergene`) REFERENCES `allergene` (`nome`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Partecipante Intolleranza` FOREIGN KEY (`fk_partecipante`) REFERENCES `partecipante` (`codice_fiscale`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `modalita`
  ADD CONSTRAINT `FK Partecipante Modalita` FOREIGN KEY (`fk_partecipante`) REFERENCES `partecipante` (`codice_fiscale`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Periodo Modalita` FOREIGN KEY (`fk_data_inizio`,`fk_data_fine`) REFERENCES `periodo` (`data_inizio`, `data_fine`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `parente`
  ADD CONSTRAINT `FK Account Parente` FOREIGN KEY (`fk_account`) REFERENCES `account` (`email`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `partecipante`
  ADD CONSTRAINT `FK Account` FOREIGN KEY (`fk_account`) REFERENCES `account` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Dieta` FOREIGN KEY (`fk_dieta`) REFERENCES `dieta` (`nome`) ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Gruppo` FOREIGN KEY (`fk_gruppo`) REFERENCES `gruppo` (`nome`) ON UPDATE CASCADE;

ALTER TABLE `presenza`
  ADD CONSTRAINT `FK Giornata Presenza Partecipante` FOREIGN KEY (`fk_giornata`) REFERENCES `giornata` (`data`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Partecipante Presenza Partecipante` FOREIGN KEY (`fk_partecipante`) REFERENCES `partecipante` (`codice_fiscale`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `recensione`
  ADD CONSTRAINT `FK Account Recensione` FOREIGN KEY (`fk_account`) REFERENCES `account` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Attivita Recensione` FOREIGN KEY (`fk_attivita`) REFERENCES `attivita` (`nome`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `risposta`
  ADD CONSTRAINT `FK Account Risposta` FOREIGN KEY (`fk_account`) REFERENCES `account` (`email`) ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Discussione Risposta` FOREIGN KEY (`fk_discussione`) REFERENCES `discussione` (`num_discussione`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `ritiro`
  ADD CONSTRAINT `FK Parente Ritiro` FOREIGN KEY (`fk_parente`) REFERENCES `parente` (`codice_fiscale`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Partecipante Ritiro` FOREIGN KEY (`fk_partecipante`) REFERENCES `partecipante` (`codice_fiscale`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `svolgimento`
  ADD CONSTRAINT `FK Attivita Svolgimento` FOREIGN KEY (`fk_attivita`) REFERENCES `attivita` (`nome`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Giornata Svolgimento` FOREIGN KEY (`fk_giornata`) REFERENCES `giornata` (`data`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK Gruppo Svolgimento` FOREIGN KEY (`fk_gruppo`) REFERENCES `gruppo` (`nome`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `volontario`
  ADD CONSTRAINT `FK Account Volontario` FOREIGN KEY (`fk_account`) REFERENCES `account` (`email`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;
