package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.PopularityComparator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    //перенесите сюда всю логику хранения, обновления и поиска объектов.
    private final UserStorage userStorage;
    private final PopularityComparator popularityComparator = new PopularityComparator();
    private final Map<Long, Film> films = new HashMap<>();
    private long counterIdFilm = 1;

    @Autowired
    public InMemoryFilmStorage(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public long generateIdFilm() {
        long id = counterIdFilm++;
        return id;
    }

    @Override
    public Film addFilm(Film film) {
        film.setId(generateIdFilm());
        films.put(film.getId(), film);
        return films.get(film.getId());
    }

    @Override
    public Film updateFilm(long id, Film film) {
        if (!films.containsKey(film.getId())) {
            throw new FilmNotFoundException(String.format("Фильма с id %d нет.", id));
        }
        films.put(id, film);
        return films.get(film.getId());
    }

    @Override
    public List<Film> findAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film findFilmById(long id) {
        if (!films.containsKey(id)) {
            throw new FilmNotFoundException(String.format("Фильм с id %d не найден", id));
        }
        return films.get(id);
    }

    @Override
    public List<Film> getPopularFilms(int count) {
        return findAllFilms().stream().sorted(popularityComparator).limit(count).collect(Collectors.toList());
    }

    @Override
    public Film deleteLike(long filmId, long userId) {
        if (!films.containsKey(filmId)) {
            throw new FilmNotFoundException(String.format("Фильм с id %d не найден", filmId));
        }
        if (!userStorage.findAllUser().contains(userStorage.findUserById(userId))) {
            throw new UserNotFoundException(String.format("Пользователь с id %d не найден", userId));
        }
        Film film = films.get(filmId);
        film.getLikes().remove(userId);
        films.put(film.getId(), film);
        return films.get(filmId);
    }

    @Override
    public Film addLike(long filmId, long userId) {
        if (!films.containsKey(filmId)) {
            throw new FilmNotFoundException(String.format("Фильм с id %d не найден", filmId));
        }
        if (!userStorage.findAllUser().contains(userStorage.findUserById(userId))) {
            throw new UserNotFoundException(String.format("Пользователь с id %d не найден", userId));
        }
        Film film = films.get(filmId);
        film.getLikes().add(userId);
        films.put(film.getId(), film);
        return films.get(filmId);
    }

}
