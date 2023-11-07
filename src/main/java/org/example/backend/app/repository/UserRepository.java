package org.example.backend.app.repository;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.Getter;
import org.example.backend.app.models.User;
import org.example.backend.daos.UserDao;

import java.util.ArrayList;

public class UserRepository implements Repository {
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    UserDao userDao;
    public UserRepository(UserDao userDao) {
        setUserDao(userDao);
    }
    @Override
    public Object get(int id) {
        return null;
    }

    @Override
    public ArrayList getAll() {
        ArrayList<User> users = getUserDao().readAll();
        return null;
    }

    @Override
    public void add(User user) {
        getUserDao().create(user);
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void remove(int id) {

    }
}
