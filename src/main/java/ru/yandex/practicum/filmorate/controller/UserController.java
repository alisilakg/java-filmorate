package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int counterIdUser = 1;
    public int generateIdUser() {
        int id = counterIdUser;
        counterIdUser++;
        return id;
    }
    @GetMapping
    public ArrayList<User> findAll() {
        return new ArrayList<>(users.values());
    }
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("POST request received: {}", user);
        if(user.getEmail() == null || user.getEmail().isBlank()) {
            log.error("User email empty");
            throw new ValidationException("Адрес электронной почты не может быть пустым.");
        }
        if(!user.getEmail().contains("@")) {
            log.error("User email does not contain the symbol @");
            throw new ValidationException("Адрес электронной почты должен содержать символ @.");
        }
        if(user.getLogin() == null || user.getLogin().isBlank()) {
            log.error("User login empty");
            throw new ValidationException("Логин не может быть пустым.");
        }
        if(user.getLogin().contains(" ")) {
            log.error("User login contains spaces");
            throw new ValidationException("Логин не может содержать пробелы.");
        }
        if(user.getName() == null || user.getName().isBlank() || user.getName().isEmpty()) {
            log.error("User name empty. Set login {} as name", user.getLogin());
            user.setName(user.getLogin());
        }
        if(user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Birthday in the future");
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
        user.setId(generateIdUser());
        users.put(user.getId(), user);
        log.info("User added: {}", user);
        return user;
    }
    @PutMapping
    public User put(@Valid @RequestBody User user){
        log.info("PUT request received: {}", user);
        if(!users.containsKey(user.getId())) {
            log.error("User's id is not exist");
            throw new ValidationException("Пользователя с таким id нет.");
        }
        if(user.getEmail() == null || user.getEmail().isBlank()) {
            log.error("User email empty");
            throw new ValidationException("Адрес электронной почты не может быть пустым.");
        }
        if(!user.getEmail().contains("@")) {
            log.error("User email does not contain the symbol @");
            throw new ValidationException("Адрес электронной почты должен содержать символ @.");
        }
        if(user.getLogin() == null || user.getLogin().isBlank()) {
            log.error("User login empty");
            throw new ValidationException("Логин не может быть пустым.");
        }
        if(user.getLogin().contains(" ")) {
            log.error("User login contains spaces");
            throw new ValidationException("Логин не может содержать пробелы.");
        }
        if(user.getName() == null || user.getName().isBlank() || user.getName().isEmpty()) {
            log.error("User name empty. Set login {} as name", user.getLogin());
            user.setName(user.getLogin());
            System.out.println("Вместо имени используется логин, так как поле для имени осталось пустымю");
        }
        if(user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Birthday in the future");
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
        users.put(user.getId(), user);
        return user;
    }
}