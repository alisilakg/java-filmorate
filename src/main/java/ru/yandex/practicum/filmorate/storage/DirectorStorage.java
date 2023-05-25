package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface DirectorStorage {
    List<Director> findAllDirectors();

    Director findDirectorById(long directorId);

    Director addDirector(Director director);

    Director updateDirector(long id, Director director);

    void deleteDirector(long id);

    List<Film> getListOfFilmsByDirectorSortByYear(long id);

    List<Film> getListOfFilmsByDirectorSortByLikes(long id);
}
