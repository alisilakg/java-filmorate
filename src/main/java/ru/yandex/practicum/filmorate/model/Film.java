package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Data
public class Film {
    private long id;
    @NotBlank
    @NotEmpty
    private String name;
    @NotBlank
    @NotEmpty
    @Size(max = 200)
    private String description;
    @NotBlank
    @NotEmpty
    private LocalDate releaseDate;
    @NotBlank
    @NotEmpty
    @Positive
    private int duration;
    private Set<Genre> genres = new HashSet<>();
    private Mpa mpa;
    private Set<Director> directors = new HashSet<>();
    @JsonIgnore
    private Set<Long> likes = new HashSet<>();

    public Film() {
    }

    public Film(String name, String description, LocalDate releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;

    }

    public Film(String name, String description, LocalDate releaseDate, int duration, Set<Genre> genres, Mpa mpa, Set<Director> directors) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.genres = genres;
        this.mpa = mpa;
        this.directors = directors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
