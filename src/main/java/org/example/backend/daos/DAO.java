package org.example.backend.daos;

import org.example.backend.app.models.User;

import java.util.ArrayList;

public interface DAO<T> {
    void create(T type);
    T read(int id);
    void update(T type);
    void delete(int id);
    ArrayList<T> readAll();
}
