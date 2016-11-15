# DC schema

# --- !Ups

CREATE TABLE ITEMS (
    ID integer NOT NULL AUTO_INCREMENT PRIMARY KEY,
    FIRST_NAME varchar(255) NOT NULL,
    LAST_NAME varchar(255) NOT NULL,
    PET_ID varchar(255) NOT NULL
);

# --- !Downs

DROP TABLE ITEMS;
