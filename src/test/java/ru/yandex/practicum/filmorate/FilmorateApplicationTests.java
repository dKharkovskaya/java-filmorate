package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.memory.DbFilmStorage;
import ru.yandex.practicum.filmorate.storage.memory.DbMpaStorage;
import ru.yandex.practicum.filmorate.storage.memory.DbUserStorage;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
    private final DbUserStorage dbUserStorage;
    private final DbFilmStorage dbFilmStorage;
    private final DbMpaStorage dbMpaStorage;

    @Test
    @Order(1)
    public void addUser() {
        User user = new User(
                (int) 1,
                "test@test.com",
                "test",
                "Name",
                LocalDate.of(1991, 6, 1),
                Set.of(0)
        );

        dbUserStorage.create(user);

        User addedUser = dbUserStorage.findById(1);

        assertThat(addedUser).isNotNull();
        assertThat(addedUser.getId()).isEqualTo(user.getId());
        assertThat(addedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(addedUser.getLogin()).isEqualTo(user.getLogin());
        assertThat(addedUser.getName()).isEqualTo(user.getName());
        assertThat(addedUser.getBirthday()).isEqualTo(user.getBirthday());
    }

    @Test
    @Order(2)
    public void addFilm() {
        Mpa mpa = dbMpaStorage.findById(1);
        assertThat(mpa).isNotNull();

        Film film = new Film(
                 1,
                "New film",
                "New Description",
                LocalDate.of(3000, 8, 1),
                1,
                new LinkedHashSet<Genre>(),
                new Mpa(),
                Set.of(3)
        );
        film.setMpa(mpa);

        dbFilmStorage.create(film);
        Film addedFilm = dbFilmStorage.findById(1);

        assertThat(addedFilm).isNotNull();
        assertThat(addedFilm.getId()).isEqualTo(film.getId());
        assertThat(addedFilm.getName()).isEqualTo(film.getName());
        assertThat(addedFilm.getDescription()).isEqualTo(film.getDescription());
        assertThat(addedFilm.getReleaseDate()).isEqualTo(film.getReleaseDate());
        assertThat(addedFilm.getDuration()).isEqualTo(film.getDuration());
        assertThat(addedFilm.getMpa()).isEqualTo(film.getMpa());
    }
}
