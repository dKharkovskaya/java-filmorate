DROP TABLE IF EXISTS users, mpa, films,  genres, films_genres, film_likes, friends CASCADE;

CREATE TABLE IF NOT EXISTS users (
    id  INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email    VARCHAR(256) NOT NULL,
    login    VARCHAR(256) NOT NULL,
    name     VARCHAR(256) ,
    birthday DATE
);

CREATE TABLE IF NOT EXISTS genres (
    id  INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name    VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS mpa (
    id  INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name    VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS friends (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id INTEGER REFERENCES users (id),
    friend_id INTEGER REFERENCES users (id),
    friendStatus BOOLEAN
);

alter table friends alter COLUMN friendStatus SET DEFAULT FALSE;

CREATE TABLE IF NOT EXISTS films (
    id  INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name           VARCHAR(256) NOT NULL,
    description    VARCHAR(256) ,
    mpa_id         INTEGER REFERENCES mpa (id),
    releaseDate    DATE         ,
    duration       INTEGER
);

CREATE TABLE IF NOT EXISTS film_likes (
    user_id INTEGER REFERENCES users (id),
    film_id INTEGER REFERENCES films (id),
    PRIMARY KEY(film_id, user_id)
);

CREATE TABLE IF NOT EXISTS films_genres (
    film_id INTEGER REFERENCES films (id),
    genre_id INTEGER REFERENCES genres (id),
    PRIMARY KEY(film_id, genre_id)
);

