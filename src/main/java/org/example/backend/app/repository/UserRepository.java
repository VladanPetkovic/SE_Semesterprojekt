package org.example.backend.app.repository;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.Getter;
import org.example.backend.app.models.User;
import org.example.backend.app.models.UserData;
import org.example.backend.daos.UserDAO;

import java.util.ArrayList;

public class UserRepository implements Repository<User> {
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    UserDAO userDao;

    public UserRepository(UserDAO userDao) {
        setUserDao(userDao);
    }

    @Override
    public User get(int id) {
        return getUserDao().read(id);
    }

    public User get(String name) {
        return getUserDao().read(name);
    }

    public User get(String name, String password) {
        return getUserDao().read(name, password);
    }

    @Override
    public ArrayList<User> getAll() {
        return getUserDao().readAll();
    }

    @Override
    public void add(User user) {
        getUserDao().create(user);
    }

    @Override
    public void update(User user) {
        getUserDao().update(user);
    }

    public void updateElo(int user_id, int elo) {
        getUserDao().updateElo(user_id, elo);
    }

    public void update(int coins, int user_id) {
        getUserDao().update(coins, user_id);
    }

    public void update(User user, UserData newUserData) {
        getUserDao().update(
                user.getUser_id(),
                newUserData.getName(),
                newUserData.getBio(),
                newUserData.getImage()
        );
    }

    @Override
    public void remove(int id) {
        getUserDao().delete(id);
    }
}
