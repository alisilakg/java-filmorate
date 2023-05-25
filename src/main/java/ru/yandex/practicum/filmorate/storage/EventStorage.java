package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Event;

import java.util.List;

public interface EventStorage {

    List<Event> getEventById(long id);

    void addLike(long filmId, long userId);

    void deleteLike(long filmId, long userId);

    void addFriend(long userId, long friendId);

    void deleteFriend(long userId, long friendId);

    void addReview(long userId, long reviewId);

    void deleteReview(long userId, long reviewId);

    void updateReview(long userId, long reviewId);

}
