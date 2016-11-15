# DC schema

# --- !Ups

CREATE TABLE PETS (
    ID integer NOT NULL AUTO_INCREMENT PRIMARY KEY,
    NAME varchar(255) NOT NULL,
    NOTES varchar(255) NOT NULL,
);

# --- !Downs

DROP TABLE PETS;
