package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    public Collection<Film> findAllFilms();

    public Film findFilmsById(Integer id);

    public Film create(Film film);

    public Film update(Film film);
}
