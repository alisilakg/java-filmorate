package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Director;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DirectorMapper implements RowMapper<Director> {
    @Override
    public Director mapRow(ResultSet rs, int rowNum) throws SQLException {
        Director director = new Director();

        director.setId(rs.getLong("DIRECTOR_ID"));
        director.setName(rs.getString("DIRECTOR_NAME"));

        return director;
    }
}
