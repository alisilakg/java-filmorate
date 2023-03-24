package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private int counterIdFilm = 1;
    public int generateIdFilm() {
        int id = counterIdFilm;
        counterIdFilm++;
        return id;
    }
    @GetMapping
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }
    @PostMapping
    public Film create(@RequestBody Film film) {
        log.info("POST request received: {}", film);
        if(film.getName() == null || film.getName().isBlank() || film.getName().isEmpty()) {
            log.error("Film name empty");
            throw new ValidationException("Название фильма не может быть пустым.");
        }
        if(film.getDescription().length() > 200) {
            log.error("Film's description longer than 200 characters");
            throw new ValidationException("Описание фильма должно быть меньше 200 символов.");
        }
        if(film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("Release date earlier than 12/28/1895");
            throw new ValidationException("Дата релиза не может быть раньше 28.12.1895.");
        }
        if(film.getDuration() < 0) {
            log.error("Duration of the film should not be negative");
            throw new ValidationException("Продолжительность фильма не должна быть отрицательной.");
        }
        film.setId(generateIdFilm());
        films.put(film.getId(), film);
        return film;
    }
    @PutMapping
    public Film put(@RequestBody Film film) {
        log.info("PUT request received: {}", film);
        if(!films.containsKey(film.getId())) {
            log.error("Film's id is not exist");
            throw new ValidationException("Фильма с таким id нет.");
        }
        if(film.getName() == null || film.getName().isBlank() || film.getName().isEmpty()) {
            log.error("Film name empty");
            throw new ValidationException("Название фильма не может быть пустым.");
        }
        if(film.getDescription().length() > 200) {
            log.error("Film's description longer than 200 characters");
            throw new ValidationException("Описание фильма должно быть меньше 200 символов.");
        }
        if(film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("Release date earlier than 12/28/1895");
            throw new ValidationException("Дата релиза не может быть раньше 28.12.1895.");
        }
        if(film.getDuration() < 0) {
            log.error("Duration of the film should not be negative");
            throw new ValidationException("Продолжительность фильма не должна быть отрицательной.");
        }
        films.put(film.getId(), film);
        return film;
    }
}
