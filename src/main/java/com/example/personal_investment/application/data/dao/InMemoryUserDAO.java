package com.example.personal_investment.application.data.dao;

import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.exceptions.EntityNotExistsException;
import com.example.personal_investment.domain.usecases.user.UserDAO;

import java.util.*;

public class InMemoryUserDAO implements UserDAO {

    private static final Map<String, User> db = new LinkedHashMap<>();

    @Override
    public String insert(User user) {
        db.put(user.getUsername(), user);
        return user.getUsername();
    }

    @Override
    public Optional<User> findOne(User user) {
        if (db.containsKey(user.getUsername())) {
            return Optional.of(db.get(user.getUsername()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        if (db.containsKey(username)) {
            return Optional.of(db.get(username));
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public void update(User user) {
        String id = user.getUsername();
        if (db.containsKey(id)) {
            db.replace(id, user);
        }
        throw new EntityNotExistsException("Cannot update, user not exists");
    }

    @Override
    public void delete(User user) {
        if (!db.containsKey(user.getUsername())) {
            throw new EntityNotExistsException("Cannot delete, user not exists");
        }
        db.remove(user.getUsername());
    }
}
