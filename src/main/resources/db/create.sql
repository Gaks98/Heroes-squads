SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS heroes (
  id int PRIMARY KEY auto_increment,
  description VARCHAR,
  assigned BOOLEAN,
  squadid INTEGER
);

CREATE TABLE IF NOT EXISTS squads (
  id int PRIMARY KEY auto_increment,
  name VARCHAR
);