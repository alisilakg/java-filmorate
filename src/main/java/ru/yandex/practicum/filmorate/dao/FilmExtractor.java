package ru.yandex.practicum.filmorate.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FilmExtractor implements ResultSetExtractor<Map<Film, List<Genre>>> {
    @Override
    public Map<Film, List<Genre>> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Film, List<Genre>> data = new HashMap<>();
        while (rs.next()) {
            Film film = new Film();
            film.setId(rs.getInt("FILM_ID"));
            film.setName(rs.getString("FILM_NAME"));
            film.setDescription(rs.getString("DESCRIPTION"));
            film.setReleaseDate(rs.getDate("RELEASE_DATE").toLocalDate());
            film.setDuration(rs.getInt("DURATION"));

            Mpa mpa = new Mpa();
            mpa.setId(rs.getInt("MPA_ID"));
            mpa.setName(rs.getString("MPA_NAME"));
            film.setMpa(mpa);

            Genre genre = new Genre();
            genre.setId(rs.getInt("GENRE_ID"));
            genre.setName(rs.getString("GENRE_NAME"));

            if (!data.containsKey(film)) {
                data.put(film, new ArrayList<>());
            }
            data.get(film).add(genre);
        }
        return data;
    }

}
