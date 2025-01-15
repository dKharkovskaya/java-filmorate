package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
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
        log.info("GET /user");
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        log.info("POST /user/{}", user.getName());
        // проверяем выполнение необходимых условий
        validateUser(user);
        // формируем дополнительные данные
        user.setId(getNextId());
        // сохраняем нового пользователя в памяти приложения
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        if (log.isInfoEnabled()) {
            log.info("PUT /user/{}", user.getName());
        }
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("Пост с id = " + user.getId() + " не найден");
        }
        User oldUser = users.get(user.getId());
        validateUser(user);
        // если пользователь найден и все условия соблюдены, обновляем её содержимое
        oldUser.setName(user.getName());
        oldUser.setBirthday(user.getBirthday());
        oldUser.setLogin(user.getLogin());
        oldUser.setEmail(user.getEmail());
        return oldUser;

    }

    private void validateUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("логин не может быть пустым и содержать пробелы");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("дата рождения не может быть в будущем");
        }
    }

    // вспомогательный метод для генерации идентификатора нового поста
    private int getNextId() {
        int currentMaxId = users.keySet().stream().mapToInt(id -> id).max().orElse(0);
        return ++currentMaxId;
    }
}
