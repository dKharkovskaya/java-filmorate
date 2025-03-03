package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

public interface MpaStorage {
    public Collection<Mpa> findAll();

    public Mpa findById(Integer id);

    public boolean deleteById(Integer id);


}
