package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

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
        return userStorage.findAllUsers();
    }

    public User createUser(User user) {
        return userStorage.create(user);
    }

    public User updateUser(User User) {
        return userStorage.update(User);
    }

    public User addFriend(Integer idUser, Integer idFriends) {
        User user = userStorage.findUsersById(idUser);
        User friend = userStorage.findUsersById(idFriends);
        if (user.getFriends().contains(friend)) {
            throw new ValidationException("Пользователи уже являются друзьями друг друга");
        }
        user.getFriends().add(friend);
        friend.getFriends().add(user);
        return user;
    }

    public User deleteFriend(Integer idUser, Integer idFriends) {
        User user = userStorage.findUsersById(idUser);
        User friend = userStorage.findUsersById(idFriends);
        if (user.getFriends().contains(friend)) {
            throw new ValidationException("Пользователи уже являются друзьями друг друга");
        }
        user.getFriends().remove(friend);
        friend.getFriends().remove(user);
        return user;
    }

    public List<User> showCommonListFriend(Integer idUser, Integer idFriends) {
        User user = userStorage.findUsersById(idUser);
        User friend = userStorage.findUsersById(idFriends);
        Set<User> currentUserFriends = user.getFriends();
        Set<User> friendFriends = friend.getFriends();
        return currentUserFriends.stream()
                .filter(friendFriends::contains)
                .map(userMap -> userStorage.findUsersById(userMap.getId()))
                .collect(Collectors.toList());
    }

    public Collection<User> getListFriends(Integer idUser) {
        User user = userStorage.findUsersById(idUser);
        log.info("Получен список друзей пользователя с id = {}", user.getId());
        return user.getFriends();
    }
}





