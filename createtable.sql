DROP DATABASE IF EXISTS `moviedb`;
CREATE DATABASE `moviedb`;
USE moviedb;

CREATE TABLE movies (
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	title varchar(100) NOT NULL,
	year int NOT NULL,
	director varchar(100) NOT NULL,
	banner_url varchar(200),
	trailer_url varchar(200)
);

ALTER TABLE  movies ADD FULLTEXT(title);

CREATE TABLE stars (
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	dob date,
	photo_url varchar(200)
);

CREATE TABLE stars_in_movies (
	star_id int NOT NULL REFERENCES stars.id,
	movie_id int NOT NULL REFERENCES movies.id
);

CREATE TABLE genres (
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name varchar(32) NOT NULL
);

CREATE TABLE genres_in_movies (
	genre_id int NOT NULL REFERENCES genres.id,
	movie_id int NOT NULL REFERENCES movies.id
);

CREATE TABLE creditcards (
	id varchar(20) NOT NULL PRIMARY KEY,
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	expiration date NOT NULL
);

CREATE TABLE customers (
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	cc_id varchar(20) NOT NULL REFERENCES creditcards.id,
	address varchar(200) NOT NULL,
	email varchar(50) NOT NULL,
	password varchar(20) NOT NULL
);

CREATE TABLE sales (
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	customer_id int NOT NULL REFERENCES customers.id,
	movie_id int NOT NULL REFERENCES movies.id,
	sale_date date NOT NULL
);

CREATE TABLE employees (
	email varchar(50) primary key,
	password varchar(20) not null,
	fullname varchar(100)
);
