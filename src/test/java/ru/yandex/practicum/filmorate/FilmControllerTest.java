package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTest {
    FilmController filmController;

    Film film;
    @BeforeEach
    void beforeEach() {
        filmController = new FilmController();
        film = Film.builder()
                .name("Film")
                .description("Very interesting film")
                .releaseDate(LocalDate.now())
                .duration(30)
                .build();
    }

    @Test
    void shouldPostFilmWithEmptyName() {
        film.setName(" ");
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> filmController.create(film));
        assertEquals("Название фильма не может быть пустым.", exception.getMessage());
    }

    @Test
    void shouldPostFilmWithDescriptionLonger200() {
        StringBuilder longDescription = new StringBuilder("Very ");
        for(int i = 1; i < 200; i++) {
            String newString = "very ";
            longDescription.append(newString);
        }
        longDescription.append("long description.");
        film.setDescription(longDescription.toString());
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> filmController.create(film));
        assertEquals("Описание фильма должно быть меньше 200 символов.", exception.getMessage());
    }

    @Test
    void shouldPostFilmWithReleaseDateEarlierThanBirthDayMovie() {
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> filmController.create(film));
        assertEquals("Дата релиза не может быть раньше 28.12.1895.", exception.getMessage());
    }

    @Test
    void shouldPostFilmWithNegativeDuration() {
        film.setDuration(-30);
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> filmController.create(film));
        assertEquals("Продолжительность фильма не должна быть отрицательной.", exception.getMessage());
    }

}
