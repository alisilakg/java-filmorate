package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.EventDbStorage;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@Service
public class UserService {
    private final UserStorage userStorage;
    @Autowired
    private EventDbStorage eventDbStorage;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(long userId, long friendId) {
        userStorage.addFriend(userId, friendId);
    }

    public void deleteFriend(long userId, long friendId) {
        userStorage.deleteFriend(userId, friendId);
    }

    public List<User> getListCommonFriends(long userId, long otherUserId) {
        return userStorage.findCommonFriends(userId, otherUserId);
    }

    public List<User> getListAllUsers() {
        return userStorage.findAllUser();
    }

    public User createUser(User user) {
        return userStorage.addUser(user);
    }

    public User updateUser(long id, User user) {
        return userStorage.updateUser(id, user);
    }

    public User getUserById(long id) {
        return userStorage.findUserById(id);
    }

    public List<User> getListFriends(long userId) {
        return userStorage.findAllFriends(userId);
    }

    public List<Event> getEventsByIdUser (long userId){
        if (!userStorage.contains(userId)){
            throw new UserNotFoundException("Пользователь отсутствует");
        }
        return eventDbStorage.getEventById(userId);
    }
}
