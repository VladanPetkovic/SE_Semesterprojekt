package org.example.backend.app.repository;

import org.example.backend.app.models.User;

import java.util.ArrayList;

public interface Repository<T> {
    T get(int id);
    ArrayList<T> getAll();
    void add(T object);
    void update(T object);
    void remove(int id);
}
