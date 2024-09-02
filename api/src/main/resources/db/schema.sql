CREATE TABLE table_vehicle (
    id bigint NOT NULL AUTO_INCREMENT,
    brand varchar(255) NOT NULL,
    color varchar(255) NOT NULL,
    model varchar(255) NOT NULL,
    plate varchar(255) NOT NULL,
    year varchar(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (plate)
);

CREATE TABLE table_clients (
  id bigint NOT NULL AUTO_INCREMENT,
  address varchar(255) NOT NULL,
  cnpj varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  phone varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY (cnpj),
  UNIQUE KEY (email)
);

CREATE TABLE table_admin (
  id bigint NOT NULL AUTO_INCREMENT,
  password varchar(255) NOT NULL,
  username varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY (username)
);
