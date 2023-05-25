package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> findAll() {
        return userService.getListAllUsers();
    }

    @GetMapping("/users/{id}")
    public User findById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> findListFriend(@PathVariable("id") Long userId) {
        return userService.getListFriends(userId);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> findCommonFriends(@PathVariable("id") Long userId, @PathVariable("otherId") Long otherUserId) {
        return userService.getListCommonFriends(userId, otherUserId);
    }

    @PostMapping("/users")
    public User create(@RequestBody User user) {
        log.info("POST request received: {}", user);
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            log.error("User email empty");
            throw new ValidationException("Адрес электронной почты не может быть пустым.");
        }
        if (!user.getEmail().contains("@")) {
            log.error("User email does not contain the symbol @");
            throw new ValidationException("Адрес электронной почты должен содержать символ @.");
        }
        if (user.getLogin() == null || user.getLogin().isBlank()) {
            log.error("User login empty");
            throw new ValidationException("Логин не может быть пустым.");
        }
        if (user.getLogin().contains(" ")) {
            log.error("User login contains spaces");
            throw new ValidationException("Логин не может содержать пробелы.");
        }
        if (user.getName() == null || user.getName().isBlank() || user.getName().isEmpty()) {
            log.error("User name empty. Set login {} as name", user.getLogin());
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Birthday in the future");
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
        log.info("User added: {}", user);
        return userService.createUser(user);
    }

    @PutMapping("/users")
    public User put(@RequestBody User user) {
        log.info("PUT request received: {}", user);
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            log.error("User email empty");
            throw new ValidationException("Адрес электронной почты не может быть пустым.");
        }
        if (!user.getEmail().contains("@")) {
            log.error("User email does not contain the symbol @");
            throw new ValidationException("Адрес электронной почты должен содержать символ @.");
        }
        if (user.getLogin() == null || user.getLogin().isBlank()) {
            log.error("User login empty");
            throw new ValidationException("Логин не может быть пустым.");
        }
        if (user.getLogin().contains(" ")) {
            log.error("User login contains spaces");
            throw new ValidationException("Логин не может содержать пробелы.");
        }
        if (user.getName() == null || user.getName().isBlank() || user.getName().isEmpty()) {
            log.error("User name empty. Set login {} as name", user.getLogin());
            user.setName(user.getLogin());
            System.out.println("Вместо имени используется логин, так как поле для имени осталось пустымю");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Birthday in the future");
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
        return userService.updateUser(user.getId(), user);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") Long userId, @PathVariable("friendId") Long friendId) {
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable("id") Long userId, @PathVariable("friendId") Long friendId) {
        userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/users/{id}/feed")
    public List<Event> getEventsByIdUser(@PathVariable("id") Long userId){
        return userService.getEventsByIdUser(userId);
    }

}