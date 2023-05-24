package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.DirectorService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class DirectorController {
    private final DirectorService directorService;

    @Autowired
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping("/directors")
    public List<Director> findAll() {
        return directorService.getAllDirectors();
    }

    @GetMapping("/directors/{id}")
    public Director findById(@PathVariable Long id) {
        return directorService.getDirectorById(id);
    }

    @PostMapping("/directors")
    public Director create(@RequestBody Director director) {
        log.info("POST request received: {}", director);
        if (director.getName() == null || director.getName().isBlank() || director.getName().isEmpty()) {
            log.error("Director name empty");
            throw new ValidationException("Фамилия режиссёра не может быть пустой.");
        }
        return directorService.createDirector(director);
    }

    @PutMapping("/directors")
    public Director put(@RequestBody Director director) {
        log.info("PUT request received: {}", director);
        if (director.getName() == null || director.getName().isBlank() || director.getName().isEmpty()) {
            log.error("Director name empty");
            throw new ValidationException("Фамилия режиссёра не может быть пустой.");
        }
        return directorService.updateDirector(director.getId(), director);
    }

    @DeleteMapping("/directors/{id}")
    public void deleteDirector(@PathVariable Long id) {
        directorService.deleteDirector(id);
    }

    @GetMapping("/films/director/{id}")
    public List<Film> getListFilmsByDirector(@PathVariable Long id,
                                             @RequestParam String sortBy) {
        List<Film> films = new ArrayList<>();
        if (sortBy.equals("likes")) {
            films = directorService.getListOfFilmsByDirectorSortByLikes(id);
        }
        if (sortBy.equals("year")) {
            films = directorService.getListOfFilmsByDirectorSortByYear(id);
        }

        return films;
    }

}
