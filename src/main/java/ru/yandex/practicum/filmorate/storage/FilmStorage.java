package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    public Collection<Film> findAll();

    public Film findById(Integer id);

    public Film create(Film film);

    public Film update(Film film);

    public boolean deleteById(Integer id);

}
