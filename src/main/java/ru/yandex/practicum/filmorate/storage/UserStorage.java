package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    //методы добавления, удаления и модификации объектов.
    User addUser(User user);

    User updateUser(long id, User user);

    List<User> findAllUser();

    User findUserById(long id);

    void addFriend(long userId, long friendId);

    void deleteFriend(long userId, long friendId);

    List<User> findAllFriends(long userId);

    List<User> findCommonFriends(long userId, long otherUserId);

    boolean contains(long id);
}

