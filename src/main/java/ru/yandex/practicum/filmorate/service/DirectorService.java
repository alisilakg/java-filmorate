package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.DirectorStorage;

import java.util.List;

@Service
public class DirectorService {
    private final DirectorStorage directorStorage;

    @Autowired
    public DirectorService(DirectorStorage directorStorage) {
        this.directorStorage = directorStorage;
    }

    public List<Director> getAllDirectors() {
        return directorStorage.findAllDirectors();
    }

    public Director getDirectorById(long directorId) {
        return directorStorage.findDirectorById(directorId);
    }

    public Director createDirector(Director director) {
        return directorStorage.addDirector(director);
    }

    public Director updateDirector(long id, Director director) {
        return directorStorage.updateDirector(id, director);
    }

    public void deleteDirector(long id) {
        directorStorage.deleteDirector(id);
    }

    public List<Film> getListOfFilmsByDirectorSortByYear(long id) {
        return directorStorage.getListOfFilmsByDirectorSortByYear(id);
    }

    public List<Film> getListOfFilmsByDirectorSortByLikes(long id) {
        return directorStorage.getListOfFilmsByDirectorSortByLikes(id);
    }
}
