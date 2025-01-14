package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAllUsers() {
        //log.info("GET /user");
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        log.info("POST /user/{}", user.getName());
        // проверяем выполнение необходимых условий
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ConditionsNotMetException("электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ConditionsNotMetException("логин не может быть пустым и содержать пробелы");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ConditionsNotMetException("дата рождения не может быть в будущем");
        }

        // формируем дополнительные данные
        user.setId(getNextId());
        // сохраняем нового пользователя в памяти приложения
        users.put(user.getId(), user);
        return user;
    }

    // вспомогательный метод для генерации идентификатора нового поста
    private int getNextId() {
        int currentMaxId = users.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        if (log.isInfoEnabled()) {
            log.info("PUT /user/{}", user.getName());
        }
        if (user.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }
        if (users.containsKey(user.getId())) {
            User oldUser = users.get(user.getId());
            if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
                throw new ConditionsNotMetException("электронная почта не может быть пустой и должна содержать символ @");
            }
            if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
                throw new ConditionsNotMetException("логин не может быть пустым и содержать пробелы");
            }
            if (user.getName() == null || user.getName().isBlank()) {
                user.setName(user.getLogin());
            }
            if (user.getBirthday().isAfter(LocalDate.now())) {
                throw new ConditionsNotMetException("дата рождения не может быть в будущем");
            }
            // если пользователь найден и все условия соблюдены, обновляем её содержимое
            oldUser.setName(user.getName());
            oldUser.setBirthday(user.getBirthday());
            oldUser.setLogin(user.getLogin());
            oldUser.setEmail(user.getEmail());
            return oldUser;
        }
        throw new NotFoundException("Пост с id = " + user.getId() + " не найден");
    }

}
