package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class FilmorateApplicationTests {
    static User user1;
    static FilmController filmController = new FilmController();
    static Film film1;

    @BeforeAll
    static void setUp() {
        user1 = new User("user@ty.ru", "user",  LocalDate.of(1990, 1, 1));
        film1 = new Film(0, "film1", "film1 description", LocalDate.of(2000, 2, 2), 120);
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

}
