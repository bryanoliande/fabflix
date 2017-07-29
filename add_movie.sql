USE moviedb;

DROP PROCEDURE IF EXISTS add_movie;
DELIMITER $$
CREATE PROCEDURE `add_movie`(movieID INT, movieTitle varchar(100), movieYear INT, movieDirector varchar(100), movieBannerURL varchar(200),
							  movieTrailerURL varchar(200), starID INT, starFirstName varchar(50), starLastName varchar(50), starDOB date,
							  starPhotoURL varchar(200), genreID INT, genreName varchar(32), OUT log varchar(500))
BEGIN
	DECLARE realmovieID INT;
	DECLARE realgenreID INT;
    DECLARE realstarID INT;
    
    IF (SELECT COUNT(*) FROM movies WHERE movies.title = movieTitle) = 0 THEN
        INSERT INTO movies VALUES(movieID, movieTitle, movieYear, movieDirector, movieBannerURL, movieTrailerURL);
        SET log = 'added a new movie to DB. ';
	ELSE
		SET log = 'movie already exists, did not add to DB. ';
	END IF;
			
               SET realmovieID = (SELECT movies.id FROM movies WHERE movies.title = movieTitle);
               
				/*Adding stars/genres*/
				IF (SELECT COUNT(*) FROM stars WHERE stars.first_name = starFirstName AND stars.last_name = starLastName) = 0 AND (starFirstName <> '' OR starLastName <> '') THEN
					INSERT INTO stars VALUES(starID, starFirstName, starLastName, starDOB, starPhotoURL);
					SET log = CONCAT(log, 'added a star to DB. ');
				ELSE
					SET log = CONCAT(log, 'star already exists, did not add to DB. ');
				END IF;
				
                /*if it doesnt exist and isnt empty*/
				IF(SELECT COUNT(*) FROM genres WHERE genres.name = genreName) = 0 AND (genreName <> '') THEN
					INSERT INTO genres VALUES(genreID, genreName);
					SET log = CONCAT(log, 'Added a genre to DB. ');
				   
				ELSE
					SET log = CONCAT(log, 'genre already exists, did not add to DB. ');
				END IF;
                
                /*linking stars/genres*/
                SET realgenreID = (SELECT genres.id FROM genres WHERE genres.name = genreName);
                IF(SELECT COUNT(*) FROM genres_in_movies AS gm WHERE gm.movie_id = realmovieID AND gm.genre_id = realgenreID) = 0 AND (genreName <> '') THEN
					INSERT INTO genres_in_movies VALUES(realgenreID, realmovieID);
					SET log = CONCAT(log, 'linked movie and genre. ');
                ELSE
					SET log = CONCAT(log, 'did not link movie and genre. ');
				END IF;
                
             
                SET realstarID = (SELECT stars.id FROM stars WHERE stars.first_name = starFirstName AND stars.last_name = starLastName);
                
                IF (SELECT COUNT(*) FROM stars_in_movies AS sm WHERE sm.movie_id = realmovieID AND sm.star_id = realstarID) = 0 AND (starFirstName <> '' OR starLastName <> '') THEN
					INSERT INTO stars_in_movies VALUES(realstarID, realmovieID);
					SET log = CONCAT(log, 'linked movie and star. ');
				ELSE
					SET log = CONCAT(log, 'did not link movie and star. ');
				END IF;
END$$
