DROP DATABASE IF EXISTS todo;
CREATE DATABASE todo;
USE `todo`;

CREATE TABLE `todo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `todo_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `complete` bit(1) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `todo_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_TODO_ITEM_TODO` (`todo_id`),
  CONSTRAINT `FK_TODO_ITEM_TODO` FOREIGN KEY (`todo_id`) REFERENCES `todo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
