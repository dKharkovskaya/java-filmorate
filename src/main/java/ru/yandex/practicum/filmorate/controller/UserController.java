package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping
    public Collection<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable("id") Integer id) {
        return userService.findById(id);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable Integer id) {
        return userService.getListFriends(id);
    }

    @GetMapping("/{id}/friends/common/{other-id}")
    public Collection<User> getCommonFriends(@PathVariable Integer id, @PathVariable("other-id") Integer otherId) {
        return userService.showCommonListFriend(id, otherId);
    }

    @PostMapping
    public User create(@RequestBody User user) {
        log.info("POST /user/{}", user.getName());
        return userService.createUser(user);
    }

    @PutMapping
    public User update(@RequestBody User user) {
        if (log.isInfoEnabled()) {
            log.info("PUT /user/{}", user.getName());
        }
        return userService.updateUser(user);
    }

    @PutMapping("/{id}/friends/{friend-id}")
    public User addFriend(@PathVariable Integer id, @PathVariable("friend-id") Integer friendId) {
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}")
    public User delete(@PathVariable("id") Integer id) {
        return userService.delete(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(
            @PathVariable("id") Integer id,
            @PathVariable("friendId") Integer friendId
    ) {
        return userService.deleteFriend(id, friendId);
    }

}
