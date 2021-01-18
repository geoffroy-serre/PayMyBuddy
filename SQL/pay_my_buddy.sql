-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : sam. 16 jan. 2021 à 18:46
-- Version du serveur :  8.0.21
-- Version de PHP : 7.3.21

SET
SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET
time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `pay_my_buddy`
--

-- --------------------------------------------------------

--
-- Structure de la table `friendlists`
--

DROP TABLE IF EXISTS `friendlists`;
CREATE TABLE IF NOT EXISTS `friendlists`
(
    `id`
    int
    NOT
    NULL
    AUTO_INCREMENT,
    `id_user`
    int
    NOT
    NULL,
    `id_user2`
    int
    NOT
    NULL,
    PRIMARY
    KEY
(
    `id`,
    `id_user`,
    `id_user2`
),
    KEY `fk_Friendlists_User1_idx`
(
    `id_user`
),
    KEY `fk_Friendlists_User2_idx`
(
    `id_user2`
)
    ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `friendlists`
--

INSERT INTO `friendlists` (`id`, `id_user`, `id_user2`)
VALUES (1, 1, 2),
       (2, 1, 3),
       (3, 1, 4),
       (4, 2, 3),
       (5, 2, 4),
       (6, 3, 1),
       (7, 3, 4),
       (8, 4, 1);

-- --------------------------------------------------------

--
-- Structure de la table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
CREATE TABLE IF NOT EXISTS `transactions`
(
    `id`
    int
    NOT
    NULL
    AUTO_INCREMENT,
    `amount_ht`
    double
    NOT
    NULL,
    `date`
    datetime
    NOT
    NULL,
    `id_receiver`
    int
    NOT
    NULL,
    `id_sender`
    int
    NOT
    NULL,
    `description`
    varchar
(
    45
) NOT NULL,
    PRIMARY KEY
(
    `id`,
    `id_receiver`,
    `id_sender`
),
    KEY `fk_transactions_User1_idx`
(
    `id_receiver`
),
    KEY `fk_transactions_User2_idx`
(
    `id_sender`
)
    ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `transactions`
--

INSERT INTO `transactions` (`id`, `amount_ht`, `date`, `id_receiver`, `id_sender`, `description`)
VALUES (1, 15, '2021-01-16 00:00:00', 1, 1, 'Put money on my paybuddy account'),
       (2, 300, '2021-01-16 00:00:00', 3, 2, 'Kenet to Adam'),
       (3, 15.2, '2021-01-16 00:00:00', 1, 3, 'Adam to Geff'),
       (4, 155.25, '2021-01-16 00:00:00', 4, 2, 'Adapting to mom');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users`
(
    `id`
    int
    NOT
    NULL
    AUTO_INCREMENT,
    `email`
    tinytext
    NOT
    NULL,
    `password`
    tinytext
    NOT
    NULL,
    `first_name`
    tinytext
    NOT
    NULL,
    `last_name`
    tinytext
    NOT
    NULL,
    `birthdate`
    datetime
    NOT
    NULL,
    `address`
    tinytext
    NOT
    NULL,
    `city`
    tinytext
    NOT
    NULL,
    `zip`
    tinytext
    NOT
    NULL,
    `phone`
    tinytext
    NOT
    NULL,
    `treasury`
    double
    NOT
    NULL,
    PRIMARY
    KEY
(
    `id`
)
    ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `email`, `password`, `first_name`, `last_name`, `birthdate`, `address`,
                     `city`, `zip`, `phone`, `treasury`)
VALUES (1, 'geff1982@gmail.com', '$2a$10$byLuIRvPZJp2aD8FeOh2oOu0sxn1IZWcILDTC3vPm.rEv8QKAaAgK',
        'Geff', 'Serre', '1982-04-14 00:00:00', '85 rue Jean Jaures appt 05', 'Rochefort', '17300',
        '0619457854', 0),
       (2, 'adapting@smite.com', '$2a$10$D5nyI0oVhbLPcNienm94heLH./u2cJzbbhhfSAddc1uahFD/UFYVa',
        'Kennet', 'Roos', '1990-04-14 00:00:00', '15 Vanhouten Strasse', 'Heineken', '65Ac85',
        '18954754', 544.75),
       (3, 'nodream@mgm.com', '$2a$10$tCEX2Ew9YVIpMA0JNBC2weq4VpKnW8gEl11/hhuXfAdFr2XBERsPG',
        'Adam', 'Sandler', '1975-02-11 00:00:00', '15 edler street', 'New York', 'JT65A6',
        '555052154', 284.8),
       (4, 'memere@familly.com', '$2a$10$Ug6Jlkv1321Db/IWRcMCmexC44GbR7FHnTZakrYMpJtvQq/lpa0CS',
        'Francine', 'Tison', '1959-09-24 00:00:00', '78 rue des érables', 'Valleyfield', 'JT65A6',
        '555052154', 155.25);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `friendlists`
--
ALTER TABLE `friendlists`
    ADD CONSTRAINT `fk_Friendlists_User1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `fk_Friendlists_User2` FOREIGN KEY (`id_user2`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `transactions`
--
ALTER TABLE `transactions`
    ADD CONSTRAINT `fk_transactions_User1` FOREIGN KEY (`id_receiver`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `fk_transactions_User2` FOREIGN KEY (`id_sender`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
