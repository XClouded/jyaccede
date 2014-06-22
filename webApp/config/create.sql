-- Structure de la table `article`
CREATE TABLE IF NOT EXISTS `jyaccede_article` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`name` varchar(255) NOT NULL,
	`description` text NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--------------------------------------------------------

-- Structure de la table `categorie`
CREATE TABLE IF NOT EXISTS `jyaccede_categorie` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`name` varchar(255) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

-- Structure de la table `location`
CREATE TABLE IF NOT EXISTS `jyaccede_location` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`name` varchar(255) NOT NULL,
	`latitude` double NOT NULL,
	`longitude` double NOT NULL,
	`mark` float NOT NULL,
	`idCategory` int(11) NOT NULL,
        FOREIGN KEY (idCategory) REFERENCES categorie(id),
	`disabledAccess` tinyint(1) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--------------------------------------------------------

-- Structure de la table `location`
CREATE TABLE IF NOT EXISTS `jyaccede_location` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`name` varchar(255) NOT NULL,
	`latitude` double NOT NULL,
	`longitude` double NOT NULL,
	`mark` float NOT NULL,
	`idCategory` int(11) NOT NULL,
        FOREIGN KEY (idCategory) REFERENCES categorie(id),
	`disabledAccess` tinyint(1) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;