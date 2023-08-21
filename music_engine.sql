CREATE TABLE Artists (
	id INT AUTO_INCREMENT,
    artist_name varchar(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Genres (
	id INT AUTO_INCREMENT,
    genre_name varchar(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Eras (
    id INT AUTO_INCREMENT,
    era varchar(8) NOT NULL,
    PRIMARY KEY (id)
)

CREATE TABLE Albums (
	id INT AUTO_INCREMENT,
    album_name varchar(50) NOT NULL,
    artist_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY(artist_id) REFERENCES Artists(id)
);

CREATE TABLE Songs (
	id INT AUTO_INCREMENT,
    song_title varchar(50) NOT NULL,
    artist_id INT NOT NULL,
    album_id INT,
    duration INT NOT NULL,
    genre_id INT NOT NULL,
    url varchar(100),
    FOREIGN KEY (artist_id) REFERENCES Artists(id),
    FOREIGN KEY (album_id) REFERENCES Albums(id),
    FOREIGN KEY (genre_id) REFERENCES Genres(id),
    PRIMARY KEY (id)
);

CREATE TABLE Playlists (
	id INT AUTO_INCREMENT,
    playlist_name varchar(50) NOT NULL,
    date_created DATE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE PlaylistSongs (
	id INT AUTO_INCREMENT,
    playlist_id INT NOT NULL,
    song_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY(playlist_id) REFERENCES Playlists(id),
    FOREIGN KEY(song_id) REFERENCES Songs(id)
);


drop table PlaylistSongs;
drop table Playlists;
drop table Songs;
drop table Albums;
drop table Genres;
drop table Artists;
