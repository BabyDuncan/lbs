CREATE TABLE `shop` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `longitude` double(9,6) DEFAULT NULL,
  `latitude` double(9,6) DEFAULT NULL,
  `geohash` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8