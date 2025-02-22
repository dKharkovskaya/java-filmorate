package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.memory.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.memory.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilmorateApplicationTests {
    /*static User user1;
    static FilmController filmController = new FilmController(new FilmService(new InMemoryFilmStorage(), new InMemoryUserStorage()));
    static UserController userController = new UserController(new UserService(new InMemoryUserStorage()));
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
        Film filmCreated = filmController.create(film1);
        Film updateFilm = filmCreated;
        updateFilm.setDuration(100);
        updateFilm = filmController.update(film1);
        assertEquals(100, updateFilm.getDuration());
    }

    @Test
    void findAllFilm() {
        Collection<Film> filmCollention = filmController.findAll();
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
        User updateUser = userCreated;
        updateUser.setName("Pasha");
        updateUser = userController.update(user1);
        assertEquals("Pasha", updateUser.getName());
    }

    //логин не может быть пустым и содержать пробелы
    @Test
    void createUserWithoutLogin() {
        User user = new User("ggg@uu.ru", "", LocalDate.of(2026, 1, 1));
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            User createdUser = userController.create(user);
        }, "You can't created User without login");
    }

    @Test
    public void shouldExceptionUser() {
        User user = new User("ggg", "ytyt", LocalDate.of(2026, 1, 1));
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            User createdUser = userController.create(user);
        }, "You can't created User without correct email");
    }

    @Test
    public void shouldExceptionFilm() {
        Film film = new Film("", "ytyt", LocalDate.of(2026, 1, 1), 67);
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            Film createdFilm = filmController.create(film);
        }, "You can't created Film without name");
    }*/
}
