package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserStorage userStorage;

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public User findById(Integer id) {
        return userStorage.findById(id);
    }

    public User createUser(User user) {
        validateUser(user);
        return userStorage.create(user);
    }

    public User updateUser(User user) {
        if (userStorage.findById(user.getId()) != null) {
            validateUser(user);
            return userStorage.update(user);
        }
        throw new NotFoundException("Пользователь с id = " + user.getId() + " не найден");
    }

    public User delete(Integer id) {
        User user = userStorage.findById(id);
        userStorage.deleteById(id);
        return user;
    }

    public User addFriend(Integer idUser, Integer idFriends) {
        User user;
        User friend;
        try {
            user = userStorage.findById(idUser);
            if (user == null) {
                throw new NotFoundException("Пользователь с id = " + idUser + " не найден");
            }
        } catch (Exception e) {
            throw new NotFoundException("Пользователь не найден");
        }
        try {
            friend = userStorage.findById(idFriends);
            if (friend == null) {
                throw new NotFoundException("Пользователь с id = " + idFriends + " не найден");
            }
        } catch (Exception e) {
            throw new NotFoundException("Пользователь не найден");

        }
        user.addFriend(idFriends);
        //user.getFriends().add(idFriends);
        //friend.getFriends().add(idUser);
        return userStorage.update(user);
    }

    public User deleteFriend(Integer idUser, Integer idFriends) {
        User user = userStorage.findById(idUser);
        if (user == null) {
            throw new NotFoundException("Пользователь с id = " + idUser + " не найден");
        }
        User friend = userStorage.findById(idFriends);
        if (friend == null) {
            throw new NotFoundException("Пользователь с id = " + idFriends + " не найден");
        }
        user.deleteFriend(idFriends);
        friend.deleteFriend(idUser);
        return userStorage.update(user);
    }

    public List<User> showCommonListFriend(Integer idUser, Integer idFriends) {
        User user = userStorage.findById(idUser);
        if (user == null) {
            throw new NotFoundException("Пользователь с id = " + idUser + " не найден");
        }
        User friend = userStorage.findById(idFriends);
        if (friend == null) {
            throw new NotFoundException("Пользователь с id = " + idFriends + " не найден");
        }
        Set<Integer> currentUserFriends = user.getFriends();
        Set<Integer> friendFriends = friend.getFriends();
        return currentUserFriends.stream()
                .filter(friendFriends::contains)
                .map(userStorage::findById)
                .collect(Collectors.toList());
    }

    public Collection<User> getListFriends(Integer idUser) {
        User user = userStorage.findById(idUser);
        if (user == null) {
            throw new NotFoundException("Пользователь с id = " + idUser + " не найден");
        }
        log.info("Получен список друзей пользователя с id = {}", user.getId());
        Set<Integer> userFriendsId = user.getFriends();
        ArrayList<User> userFriends = new ArrayList<>();
        for (Integer friendId : userFriendsId) {
            userFriends.add(userStorage.findById(friendId));
        }
        return userFriends;
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

}
