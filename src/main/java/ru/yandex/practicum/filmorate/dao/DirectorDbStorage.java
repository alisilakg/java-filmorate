package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.DirectorNotFoundException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.DirectorStorage;

import java.util.*;

@Repository
public class DirectorDbStorage implements DirectorStorage {
    static public final String FIND_DIRECTOR_IN_TABLE_SQL = "SELECT * FROM LIST_OF_DIRECTORS WHERE DIRECTOR_ID=?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private DirectorMapper directorMapper;

    @Autowired
    public DirectorDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Director> findAllDirectors() {
        String sql = "SELECT * FROM LIST_OF_DIRECTORS";
        return jdbcTemplate.query(sql, directorMapper);
    }

    @Override
    public Director findDirectorById(long directorId) {
        Director director = jdbcTemplate.query(FIND_DIRECTOR_IN_TABLE_SQL
                , new Object[]{directorId}, directorMapper).stream().findAny().orElse(null);
        if(director == null) {
            throw new DirectorNotFoundException("Режиссёра c таким id нет");
        }
        return director;
    }

    @Override
    public Director addDirector(Director director) {
        SimpleJdbcInsert insertIntoDirector = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("LIST_OF_DIRECTORS")
                .usingGeneratedKeyColumns("DIRECTOR_ID");
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("DIRECTOR_NAME", director.getName());
        Number newId = insertIntoDirector.executeAndReturnKey(parameters);
        director.setId(newId.longValue());

        return director;
    }

    @Override
    public Director updateDirector(long directorId, Director director) {
        Director directorExist = jdbcTemplate.query(FIND_DIRECTOR_IN_TABLE_SQL
                , new Object[]{directorId}, directorMapper).stream().findAny().orElse(null);
        if(directorExist == null) {
            throw new DirectorNotFoundException("Режиссёра c таким id нет");
        } else {
            String sqlQuery = "UPDATE LIST_OF_DIRECTORS SET DIRECTOR_NAME=? WHERE DIRECTOR_ID=?";
            jdbcTemplate.update(sqlQuery, director.getName(), directorId);
        }
        return director;
    }

    @Override
    public void deleteDirector(long id) {
        String sqlDelete = "DELETE FROM DIRECTORS WHERE DIRECTOR_ID=?";
        String sqlQuery = "DELETE FROM LIST_OF_DIRECTORS WHERE DIRECTOR_ID=?";

        jdbcTemplate.update(sqlDelete, id);
        jdbcTemplate.update(sqlQuery, id);
    }


    @Override

    public List<Film> getListOfFilmsByDirectorSortByYear(long directorId) {
        List<Film> filmsList = new ArrayList<>();
        Director director = jdbcTemplate.query(FIND_DIRECTOR_IN_TABLE_SQL
                , new Object[]{directorId}, directorMapper).stream().findAny().orElse(null);
        if(director == null) {
            throw new DirectorNotFoundException("Режиссёра c таким id нет");
        } else {
            String sql = "SELECT f.*, l.GENRE_ID, l.GENRE_NAME, m.MPA_NAME " +
                    "FROM DIRECTORS AS d " +
                    "LEFT JOIN FILMS AS f on d.FILM_ID = f.FILM_ID " +
                    "LEFT JOIN GENRES AS g ON f.FILM_ID = g.FILM_ID " +
                    "LEFT JOIN LIST_OF_GENRES AS l ON g.GENRE_ID = l.GENRE_ID " +
                    "LEFT JOIN LIST_OF_MPAS AS m on f.MPA_ID = m.MPA_ID " +
                    "WHERE d.DIRECTOR_ID=? " +
                    "ORDER BY f.RELEASE_DATE";
            Map<Film, List<Genre>> filmsWithGenre = jdbcTemplate.query(sql, new Object[]{directorId}, new FilmExtractor());

            String sqlDirectors = "SELECT d.FILM_ID,d.DIRECTOR_ID,l.DIRECTOR_NAME FROM LIST_OF_DIRECTORS AS l "
                    + "LEFT JOIN DIRECTORS AS d ON d.DIRECTOR_ID = l.DIRECTOR_ID";

            Map<Long, Set<Director>> directorsAndFilms = jdbcTemplate.query(sqlDirectors, new DirectorExtractor());

            Set<Film> films = new TreeSet(new Comparator<Film>() {
                @Override
                public int compare(Film o1, Film o2) {
                    return o1.getReleaseDate().compareTo(o2.getReleaseDate());
                }
            });
            for (Film film : filmsWithGenre.keySet()) {
                Set<Genre> genres = new HashSet<>();
                for (Genre genre : filmsWithGenre.get(film)) {
                    if (genre.getId() != 0) {
                        genres.add(genre);
                    }
                }
                film.setGenres(genres);

                Set<Director> directors = new HashSet<>();
                if(directorsAndFilms.containsKey(film.getId())) {
                    directors = directorsAndFilms.get(film.getId());
                }
                film.setDirectors(directors);

                films.add(film);
            }
            filmsList.addAll(films);
        }
        return filmsList;
    }

    @Override
    public List<Film> getListOfFilmsByDirectorSortByLikes(long directorId) {
        List<Film> films = new ArrayList<>();
        Director director = jdbcTemplate.query(FIND_DIRECTOR_IN_TABLE_SQL
                , new Object[]{directorId}, directorMapper).stream().findAny().orElse(null);
        if (director == null) {
            throw new DirectorNotFoundException("Режиссёра c таким id нет");
        } else {
            String sql = "SELECT f.*, l.GENRE_ID, l.GENRE_NAME, m.MPA_NAME, likes.COUNT_LIKES " +
                    "FROM DIRECTORS AS d " +
                    "LEFT JOIN FILMS AS f on f.FILM_ID = d.FILM_ID " +
                    "LEFT JOIN GENRES AS g ON d.FILM_ID = g.FILM_ID " +
                    "LEFT JOIN LIST_OF_GENRES AS l ON g.GENRE_ID = l.GENRE_ID " +
                    "LEFT JOIN LIST_OF_MPAS AS m on f.MPA_ID = m.MPA_ID " +
                    "LEFT JOIN (SELECT FILM_ID, COUNT(USER_ID) AS COUNT_LIKES FROM LIKES GROUP BY FILM_ID) AS likes " +
                    "on f.FILM_ID = likes.FILM_ID " +
                    "WHERE d.DIRECTOR_ID=? " +
                    "ORDER BY likes.COUNT_LIKES DESC;";
            Map<Film, List<Genre>> filmsWithGenre = jdbcTemplate.query(sql, new Object[]{directorId}, new FilmExtractor());

            String sqlDirectors = "SELECT d.FILM_ID,d.DIRECTOR_ID,l.DIRECTOR_NAME FROM LIST_OF_DIRECTORS AS l "
                    + "LEFT JOIN DIRECTORS AS d ON d.DIRECTOR_ID = l.DIRECTOR_ID";

            Map<Long, Set<Director>> directorsAndFilms = jdbcTemplate.query(sqlDirectors, new DirectorExtractor());

            for (Film film : filmsWithGenre.keySet()) {
                Set<Genre> genres = new HashSet<>();
                for (Genre genre : filmsWithGenre.get(film)) {
                    if (genre.getId() != 0) {
                        genres.add(genre);
                    }
                }
                film.setGenres(genres);

                Set<Director> directors = new HashSet<>();
                if (directorsAndFilms.containsKey(film.getId())) {
                    directors = directorsAndFilms.get(film.getId());
                }
                film.setDirectors(directors);

                films.add(film);
            }
            return films;
        }
    }

}

