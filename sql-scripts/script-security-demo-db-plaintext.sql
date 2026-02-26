USE `employee_directory`;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `enabled` tinyint NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Inserting data for table `users`
--

INSERT INTO `users`
VALUES 
	('john', '{noop}john123', 1),
	('mary', '{noop}mary123', 1),
	('susan', '{noop}susan123', 1);


CREATE TABLE `authorities` (
  `username` varchar(45) NOT NULL,
  `authority` varchar(45) NOT NULL,
  UNIQUE KEY `authorities_idx_1`  (`username`, `authority`),
  CONSTRAINT `authorities_ibfk_1`  FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


--
-- Inserting Data for table `authorities`, prefix ROLE_ is needed for Spring Security framework
--

INSERT INTO `authorities` 
VALUES 
	('john','ROLE_EMPLOYEE'),
	('mary','ROLE_EMPLOYEE'),
	('mary','ROLE_MANAGER'),
	('susan','ROLE_EMPLOYEE'),
	('susan','ROLE_MANAGER'),
	('susan','ROLE_ADMIN');


