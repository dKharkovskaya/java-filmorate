package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Collection<Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    public Film createFilm(Film film) {
        return filmStorage.create(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.update(film);
    }

    public Film addLike(Integer idFilm, Integer idUser) {
        User user = userStorage.findUsersById(idUser);
        Film film = filmStorage.findFilmsById(idFilm);
        if (user.getLikedFilms().contains(idFilm)) {
            throw new ValidationException("Пользователь " + user.getName() + " уже ставил лайк фильму " + film.getName());
        }
        user.getLikedFilms().add(film);
        film.setLike(film.getLike() + 1);
        return film;
    }

    public Film deleteLike(Integer idFilm, Integer idUser) {
        User user = userStorage.findUsersById(idUser);
        Film film = filmStorage.findFilmsById(idFilm);
        if (user.getLikedFilms().contains(idFilm)) {
            user.getLikedFilms().remove(idFilm);
            film.setLike(film.getLike() - 1);
        }
        return film;
    }

    public List<Film> getTop(Integer count) {
        List<Film> topFilms = filmStorage
                .findAllFilms()
                .stream()
                .sorted((film1, film2) -> Long.compare(film2.getLike(), film1.getLike()))
                .limit(count)
                .collect(Collectors.toList());
        return topFilms;
    }
}
