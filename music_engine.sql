CREATE TABLE Artists (
    artistName varchar(60) NOT NULL,
    PRIMARY KEY (artistName)
);

CREATE TABLE Genres (
    genreName varchar(60) NOT NULL,
    PRIMARY KEY (genreName)
);

CREATE TABLE Eras (
    era varchar(60) NOT NULL,
    PRIMARY KEY (era)
);

CREATE TABLE Albums (
    albumName varchar(60) NOT NULL,
    artist varchar(60) NOT NULL,
    PRIMARY KEY (albumName, artist),
    FOREIGN KEY(artist) REFERENCES Artists(artistName)
);

CREATE TABLE Songs (
    songTitle varchar(60) NOT NULL,
    artist varchar(60) NOT NULL,
    album varchar(60) NOT NULL,
    duration INT NOT NULL,
    genre varchar(60) NOT NULL,
    era varchar(60) NOT NULL,
    FOREIGN KEY (artist) REFERENCES Artists(artistName),
    FOREIGN KEY (album) REFERENCES Albums(albumName),
    FOREIGN KEY (genre) REFERENCES Genres(genreName),
    FOREIGN KEY (era) REFERENCES Eras(era),
    PRIMARY KEY (songTitle, artist)
);

CREATE TABLE Playlists (
    playlistName varchar(60) NOT NULL,
    dateCreated DATE NOT NULL,
    PRIMARY KEY (playlistName)
);

CREATE TABLE PlaylistSongs (
    playlist varchar(60) NOT NULL,
    song varchar(60) NOT NULL,
    artist varchar(60) NOT NULL,
    PRIMARY KEY (playlist, song, artist),
    FOREIGN KEY(playlist) REFERENCES Playlists(playlistName),
    FOREIGN KEY(song) REFERENCES Songs(songTitle),
    FOREIGN KEY(artist) REFERENCES Songs(artist)
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
