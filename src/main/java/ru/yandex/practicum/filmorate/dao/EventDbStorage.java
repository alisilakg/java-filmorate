package ru.yandex.practicum.filmorate.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.storage.EventStorage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class EventDbStorage implements EventStorage {

    private final JdbcTemplate jdbcTemplate;

    private static final String sqInsert = "INSERT INTO events " +
            "(time_event,user_id,type_id,operation_id,entity_id)" +
            " VALUES (?,?,?,?,?)";

    @Override
    public List<Event> getEventById(long id) {
        List<Event> events = new ArrayList<>();
        String sqlQ = "SELECT event_id,time_event,user_id," +
                "type_name,operation_name,entity_id " +
                "FROM (((SELECT * FROM events WHERE event_id=?) AS e " +
                "LEFT JOIN event_type AS et " +
                "ON e.type_id=et.type_id) AS e_et " +
                "LEFT JOIN operation AS o " +
                "ON e_et.operation_id=o.operation_id)";

        SqlRowSet eventRows = jdbcTemplate.queryForRowSet("select 1 from events where user_id = ?", id);
        if (eventRows.next()){
            events = jdbcTemplate.query(sqlQ,eventRowMapper(),id);
        }
        return events;
    }

    @Override
    public void addLike(long filmId, long userId) {

        int type = getIdType("LIKE");
        int opr = getIdOperation("ADD");
        LocalDate date = LocalDate.now();

        jdbcTemplate.update(sqInsert, date, userId, type, opr, filmId);
    }

    @Override
    public void deleteLike(long filmId, long userId) {

        int type = getIdType("LIKE");
        int opr = getIdOperation("REMOVE");
        LocalDateTime date = LocalDateTime.now();

        jdbcTemplate.update(sqInsert, date, userId, type, opr, filmId);
    }

    @Override
    public void addFriend(long userId, long friendId) {

        int type = getIdType("FRIEND");
        int opr = getIdOperation("ADD");
        LocalDateTime date = LocalDateTime.now();

        jdbcTemplate.update(sqInsert, date, userId, type, opr, friendId);
    }

    @Override
    public void deleteFriend(long userId, long friendId) {

        int type = getIdType("FRIEND");
        int opr = getIdOperation("REMOVE");
        LocalDateTime date = LocalDateTime.now();

        jdbcTemplate.update(sqInsert, date, userId, type, opr, friendId);
    }

    @Override
    public void addReview(long userId, long reviewId) {

        int type = getIdType("REVIEW");
        int opr = getIdOperation("ADD");
        LocalDateTime date = LocalDateTime.now();

        jdbcTemplate.update(sqInsert, date, userId, type, opr, reviewId);
    }

    @Override
    public void deleteReview(long userId, long reviewId) {

        int type = getIdType("REVIEW");
        int opr = getIdOperation("REMOVE");
        LocalDateTime date = LocalDateTime.now();

        jdbcTemplate.update(sqInsert, date, userId, type, opr, reviewId);
    }

    @Override
    public void updateReview(long userId, long reviewId) {

        int type = getIdType("REVIEW");
        int opr = getIdOperation("UPDATE");
        LocalDateTime date = LocalDateTime.now();

        jdbcTemplate.update(sqInsert, date, userId, type, opr, reviewId);
    }

    private Integer getIdType(String nameType) {
        return jdbcTemplate.queryForObject("SELECT type_id FROM event_type " +
                "WHERE type_name = ?", Integer.class, nameType);
    }

    private Integer getIdOperation(String nameOperation) {
        return jdbcTemplate.queryForObject("SELECT operation_id FROM operation " +
                "WHERE operation_name = ?", Integer.class, nameOperation);
    }

    private RowMapper<Event> eventRowMapper() {
        return (rs, rowNum) -> {
            Event event = new Event();
            event.setId(rs.getInt("event_id"));
            event.setTimestamp(rs.getTimestamp("time_event").toInstant().toEpochMilli());
            event.setUserId(rs.getInt("user_id"));
            event.setEventType(rs.getString("type_name"));
            event.setOperation(rs.getString("operation_name"));
            event.setEntityId(rs.getInt("entity_id"));

            return event;
        };
    }
}
