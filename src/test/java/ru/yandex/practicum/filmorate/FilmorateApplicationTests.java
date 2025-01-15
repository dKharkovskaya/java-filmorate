package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmorateApplicationTests {
    static User user1;
    static FilmController filmController = new FilmController();
    static UserController userController = new UserController();
    static Film film1;

    @BeforeAll
    static void setUp() {
        user1 = new User("user@ty.ru", "user", LocalDate.of(1990, 1, 1));
        film1 = new Film("film1", "film1 description", LocalDate.of(2000, 2, 2), 120);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void putFilm() {
        Film filmCreated = filmController.create(film1);
        assertNotNull(filmCreated, "putFilm does not return correct object");
        film1 = filmCreated;
    }

    @Test
    void firstUpdateFilm() {
        Film filmCreated = filmController.update(film1);
        assertNotNull(filmCreated, "updateFilm does not return correct object");
        film1 = filmCreated;
    }

    @Test
    void findAllFilm() {
        Collection<Film> filmCollention = filmController.findAllFilms();
        assertNotNull(filmCollention, "findAllFilm does not return correct object");
    }

    @Test
    void putUser() {
        User userCreated = userController.create(user1);
        assertNotNull(userCreated, "putUser does not return correct object");
        user1 = userCreated;
    }

    @Test
    void firstUpdateUser() {
        User userCreated = userController.update(user1);
        assertNotNull(userCreated, "updateUser does not return correct object");
        user1 = userCreated;
    }

    @Test
    public void shouldExceptionUser() throws ValidationException {
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            User user = new User("ggg", "ytyt", LocalDate.of(2026, 1, 1));
            User createdUser = userController.create(user);
        });
        //проверка, вылетело ли исключение. Если вылетело - то тест вернет положительный результат
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void shouldExceptionFilm() throws ValidationException {
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            Film film = new Film("", "ytyt", LocalDate.of(2026, 1, 1), 67);
            Film createdFilm = filmController.create(film);
        });
        //проверка, вылетело ли исключение. Если вылетело - то тест вернет положительный результат
        assertNotNull(thrown.getMessage());
    }
}
