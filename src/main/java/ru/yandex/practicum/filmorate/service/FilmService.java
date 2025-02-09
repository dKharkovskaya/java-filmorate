package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
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
        validateFilm(film);
        return filmStorage.create(film);
    }

    public Film updateFilm(Film film) {
        if (filmStorage.findFilmsById(film.getId()) != null) {
            validateFilm(film);
            return filmStorage.update(film);
        }
        throw new NotFoundException("Пост с id = " + film.getId() + " не найден");

    }

    public Film addLike(Integer idFilm, Integer idUser) {
        User user = userStorage.findUsersById(idUser);
        if (user == null) {
            throw new NotFoundException("Пользователь с id = " + idUser + " не найден");
        }
        Film film;
        try {
            film = filmStorage.findFilmsById(idFilm);
            if (film == null) {
                throw new NotFoundException("Фильм с id = " + idFilm + " не найден");
            }
        } catch (Exception e) {
            throw new NotFoundException("Фильм не найден");
        }
        if (user.getLikedFilms().contains(idFilm)) {
            throw new DuplicatedDataException("Пользователь " + user.getName() + " уже ставил лайк фильму " + film.getName());
        }
        user.getLikedFilms().add(idFilm);
        film.setLike(film.getLike() + 1);
        return film;
    }

    public Film deleteLike(Integer idFilm, Integer idUser) {
        User user = userStorage.findUsersById(idUser);
        if (user == null) {
            throw new NotFoundException("Пользователь с id = " + idUser + " не найден");
        }
        Film film = filmStorage.findFilmsById(idFilm);
        if (film == null) {
            throw new NotFoundException("Фильм с id = " + idFilm + " не найден");
        }
        if (user.getLikedFilms().contains(idFilm)) {
            user.getLikedFilms().remove(idFilm);
            film.setLike(film.getLike() - 1);
        }
        return film;
    }

    public List<Film> getTop(Integer count) {
        return filmStorage
                .findAllFilms()
                .stream()
                .sorted((film1, film2) -> Long.compare(film2.getLike(), film1.getLike()))
                .limit(count)
                .collect(Collectors.toList());
    }

    private void validateFilm(Film film) {
        if (film.getName().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Длина описания превышает 200 символов");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года;");
        }
    }

}
