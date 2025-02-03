package ru.yandex.practicum.filmorate.storage.memory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();

    public Collection<Film> findAllFilms() {
        return films.values();
    }

    public Film findFilmsById(Integer id) {
        for (Film film : films.values()) {
            if (Objects.equals(film.getId(), id)) {
                return film;
            }
        }
        throw new NotFoundException("Фильм с id = " + id + " не найден");
    }

    public Film create(Film film) {
        // проверяем выполнение необходимых условий
        validateFilm(film);
        // формируем дополнительные данные
        film.setId(getNextId());
        // сохраняем новый фильм в памяти приложения
        films.put(film.getId(), film);
        return film;
    }

    public Film update( Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Пост с id = " + film.getId() + " не найден");
        }
        Film oldFilm = films.get(film.getId());
        validateFilm(film);
        // если фильм найден и все условия соблюдены, обновляем её содержимое
        oldFilm.setName(film.getName());
        oldFilm.setDuration(film.getDuration());
        oldFilm.setReleaseDate(film.getReleaseDate());
        oldFilm.setDescription(film.getDescription());
        return oldFilm;
    }

    private void validateFilm(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
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

    // вспомогательный метод для генерации идентификатора нового поста
    private int getNextId() {
        int currentMaxId = films.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}
