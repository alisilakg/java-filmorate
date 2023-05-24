package ru.yandex.practicum.filmorate.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.yandex.practicum.filmorate.model.Director;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DirectorExtractor implements ResultSetExtractor<Map<Long, Set<Director>>> {
    @Override
    public Map<Long, Set<Director>> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, Set<Director>> data = new HashMap<>();
        while (rs.next()) {
            Director director = new Director();
            director.setId(rs.getInt("DIRECTOR_ID"));
            director.setName(rs.getString("DIRECTOR_NAME"));

            Long filmId = rs.getLong("FILM_ID");

            if (!data.containsKey(filmId)) {
                data.put(filmId, new HashSet<>());
            }

            data.get(filmId).add(director);
        }
        return data;
    }
}

