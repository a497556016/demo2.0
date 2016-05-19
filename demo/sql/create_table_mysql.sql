CREATE TABLE `sys_approve_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `business_key` varchar(64) NOT NULL,
  `approve_code` varchar(90) DEFAULT NULL,
  `approve_role` varchar(90) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `remark` mediumtext,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_by` varchar(45) DEFAULT 'system',
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_by` varchar(45) DEFAULT 'system',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `sys_basic_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) DEFAULT NULL,
  `code` varchar(500) DEFAULT NULL,
  `name` varchar(500) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `remark` mediumtext,
  `enable` tinyint(1) DEFAULT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_by` varchar(45) DEFAULT 'system',
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_by` varchar(45) DEFAULT 'system',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sys_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `value` varchar(100) DEFAULT NULL,
  `regex` varchar(200) DEFAULT NULL,
  `enable` tinyint(1) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_by` varchar(45) DEFAULT 'system',
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_by` varchar(45) DEFAULT 'system',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sys_icons` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(45) NOT NULL,
  `code` varchar(45) NOT NULL,
  `resource_id` varchar(45) DEFAULT NULL,
  `resource` varbinary(1024) NOT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_by` varchar(45) DEFAULT 'system',
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_by` varchar(45) DEFAULT 'system',
  PRIMARY KEY (`id`,`text`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sys_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sort_id` int(11) NOT NULL,
  `code` varchar(45) NOT NULL,
  `p_code` varchar(45) DEFAULT NULL,
  `text` varchar(450) DEFAULT NULL,
  `url` varchar(45) DEFAULT NULL,
  `type` char(1) DEFAULT '0',
  `expanded` char(1) DEFAULT '0',
  `leaf` char(1) DEFAULT '0',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_by` varchar(45) DEFAULT 'system',
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_by` varchar(45) DEFAULT 'system',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(45) DEFAULT NULL,
  `role_name` varchar(45) DEFAULT NULL,
  `remark` varchar(450) DEFAULT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_by` varchar(45) DEFAULT 'system',
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_by` varchar(45) DEFAULT 'system',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `sys_role_res_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(45) NOT NULL,
  `res_code` varchar(45) NOT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_by` varchar(45) DEFAULT 'system',
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_by` varchar(45) DEFAULT 'system',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

CREATE TABLE `sys_user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `person_code` varchar(45) NOT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(450) DEFAULT NULL,
  `locked` char(1) DEFAULT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_by` varchar(45) DEFAULT 'system',
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_by` varchar(45) DEFAULT 'system',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `person_code_UNIQUE` (`person_code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `sys_user_role_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `person_code` varchar(45) NOT NULL,
  `role_code` varchar(45) NOT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_by` varchar(45) DEFAULT 'system',
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_by` varchar(45) DEFAULT 'system',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
