CREATE TABLE Artists (
    artist_name varchar(50) NOT NULL,
    PRIMARY KEY (artist_name)
);

CREATE TABLE Genres (
    genre_name varchar(50) NOT NULL,
    PRIMARY KEY (genre_name)
);

CREATE TABLE Eras (
    era varchar(8) NOT NULL,
    PRIMARY KEY (era)
);

CREATE TABLE Albums (
    album_name varchar(50) NOT NULL,
    artist varchar(50) NOT NULL,
    PRIMARY KEY (album_name, artist),
    FOREIGN KEY(artist) REFERENCES Artists(artist_name)
);

CREATE TABLE Songs (
    song_title varchar(50) NOT NULL,
    artist varchar(50) NOT NULL,
    album varchar(50),
    duration INT,
    genre varchar(50),
    era varchar(8),
    FOREIGN KEY (artist) REFERENCES Artists(artist_name),
    FOREIGN KEY (album) REFERENCES Albums(album_name),
    FOREIGN KEY (genre) REFERENCES Genres(genre_name),
    FOREIGN KEY (era) REFERENCES Eras(era),
    PRIMARY KEY (song_title, artist)
);

CREATE TABLE Playlists (
    playlist_name varchar(50) NOT NULL,
    date_created DATE NOT NULL,
    PRIMARY KEY (playlist_name)
);

CREATE TABLE PlaylistSongs (
    playlist varchar(50) NOT NULL,
    song varchar(50) NOT NULL,
    PRIMARY KEY (playlist, song),
    FOREIGN KEY(playlist) REFERENCES Playlists(playlist_name),
    FOREIGN KEY(song) REFERENCES Songs(song_title)
);

ALTER TABLE Songs
DROP COLUMN url;

drop table PlaylistSongs;
drop table Playlists;
drop table Songs;
drop table Albums;
drop table Genres;
drop table Eras;
drop table Artists;
