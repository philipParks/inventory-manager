-- create and drop the inventory schema
DROP SCHEMA IF EXISTS inventory;
CREATE SCHEMA inventory;

-- create and drop the user table
DROP TABLE IF EXISTS inventory.user;
CREATE TABLE inventory.user (
	user_id int NOT NULL auto_increment,
  user_name varchar(20) NOT NULL,
  password varchar(20) NOT NULL,
  admin_account boolean NOT NULL DEFAULT false,
  last_modified timestamp NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
  created timestamp NOT NULL DEFAULT current_timestamp,
  PRIMARY KEY (user_id)
);

INSERT INTO inventory.user (user_name, password, admin_account) VALUES ('admin', 'admin', true);
INSERT INTO inventory.user (user_name, password) VALUES ('test', 'test');

-- create and drop the part table
DROP TABLE IF EXISTS inventory.part;
CREATE TABLE inventory.part (
	part_id int NOT NULL auto_increment,
  part varchar(100),
  location varchar(25),
  created timestamp NOT NULL DEFAULT current_timestamp,
  created_by int NOT NULL,
  last_modified timestamp NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
  modified_by int NOT NULL,
  PRIMARY KEY (part_id),
  FOREIGN KEY (created_by) REFERENCES inventory.user(user_id),
  FOREIGN KEY (modified_by) REFERENCES inventory.user(user_id)
);

-- create and drop the product table
DROP TABLE IF EXISTS inventory.engine;
CREATE TABLE inventory.engine (
	engine_id int NOT NULL auto_increment,
  engine varchar(100),
  serial_number varchar(30),
  fuel_type varchar(25),
  cylinder varchar(25),
  status varchar(25),
  location varchar(25),
  created timestamp NOT NULL DEFAULT current_timestamp,
  created_by int NOT NULL,
  last_modified timestamp NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
  modified_by int NOT NULL,
  PRIMARY KEY (engine_id),
  FOREIGN KEY (created_by) REFERENCES inventory.user(user_id),
  FOREIGN KEY (modified_by) REFERENCES inventory.user(user_id)
);

-- create and drop the product part table
DROP TABLE IF EXISTS inventory.engine_part;
CREATE TABLE inventory.engine_part (
	id int NOT NULL auto_increment,
  engine_id int NOT NULL,
  part_id int NOT NULL,
  created timestamp NOT NULL DEFAULT current_timestamp,
  created_by int,
  last_modified timestamp NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
  modified_by int,
  PRIMARY KEY (id),
  FOREIGN KEY (engine_id) REFERENCES inventory.engine(engine_id),
  FOREIGN KEY (part_id) REFERENCES inventory.part(part_id),
  FOREIGN KEY (created_by) REFERENCES inventory.user(user_id),
  FOREIGN KEY (modified_by) REFERENCES inventory.user(user_id)
);

-- create and drop workstations
DROP TABLE IF EXISTS inventory.workstation;
CREATE TABLE inventory.workstation (
	workstation_id int NOT NULL auto_increment,
  nomenclature varchar(30),
  created timestamp NOT NULL DEFAULT current_timestamp,
  created_by int NOT NULL,
  last_modified timestamp NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
  modified_by int NOT NULL,
  PRIMARY KEY (workstation_id),
  FOREIGN KEY (created_by) REFERENCES inventory.user(user_id),
  FOREIGN KEY (modified_by) REFERENCES inventory.user(user_id)
);

-- create and drop an in house part
DROP TABLE IF EXISTS inventory.inhouse_part;
CREATE TABLE inventory.inhouse_part (
	inhouse_id int NOT NULL auto_increment,
  workstation_id int NOT NULL,
  part_id int NOT NULL,
  created timestamp NOT NULL DEFAULT current_timestamp,
  created_by int NOT NULL,
  last_modified timestamp NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
  modified_by int NOT NULL,
  PRIMARY KEY (inhouse_id),
  FOREIGN KEY (workstation_id) REFERENCES inventory.workstation(workstation_id),
  FOREIGN KEY (part_id) REFERENCES inventory.part(part_id),
  FOREIGN KEY (created_by) REFERENCES inventory.user(user_id),
  FOREIGN KEY (modified_by) REFERENCES inventory.user(user_id)
);

-- create and drop distributor table
DROP TABLE IF EXISTS inventory.distributor;
CREATE TABLE inventory.distributor (
	distributor_id int NOT NULL auto_increment,
  distributor varchar(30),
  street varchar(50),
  city varchar(50),
  state varchar(2),
  zipcode varchar(5),
  phone varchar(10),
  contact_name varchar(50),
  contact_email varchar(50),
  created timestamp NOT NULL DEFAULT current_timestamp,
  created_by int NOT NULL,
  last_modified timestamp NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
  modified_by int NOT NULL,
  PRIMARY KEY (distributor_id),
  FOREIGN KEY (created_by) REFERENCES inventory.user(user_id),
  FOREIGN KEY (modified_by) REFERENCES inventory.user(user_id)
);

-- create and drop an outsource part
DROP TABLE IF EXISTS inventory.outsource_part;
CREATE TABLE inventory.outsource_part (
	outsource_id int NOT NULL auto_increment,
  distributor_id int NOT NULL,
  part_id int NOT NULL,
  created timestamp NOT NULL DEFAULT current_timestamp,
  created_by int NOT NULL,
  last_modified timestamp NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
  modified_by int NOT NULL,
  PRIMARY KEY (outsource_id),
	FOREIGN KEY (part_id) REFERENCES inventory.part(part_id),
  FOREIGN KEY (distributor_id) REFERENCES inventory.distributor(distributor_id),
  FOREIGN KEY (created_by) REFERENCES inventory.user(user_id),
  FOREIGN KEY (modified_by) REFERENCES inventory.user(user_id)
);
