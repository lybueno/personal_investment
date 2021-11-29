package com.example.personal_investment.application.data.inmemory;

import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.exceptions.EntityNotExistsException;
import com.example.personal_investment.domain.usecases.user.UserDAO;

import java.util.*;

public class InMemoryUserDAO implements UserDAO {

    private static final Map<String, User> db = new LinkedHashMap<>();

    @Override
    public String insert(User user) {
        db.put(user.getId(), user);
        return user.getUsername();
    }

    @Override
    public Optional<User> findOne(String id) {
        if (db.containsKey(id)) {
            return Optional.of(db.get(id));
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public void update(User user) {
        String id = user.getId();
        if (db.containsKey(id)) {
            db.replace(id, user);
        }
        throw new EntityNotExistsException("Cannot update, user not exists");
    }

    @Override
    public void delete(User user) {
        if (!db.containsKey(user.getId())) {
            throw new EntityNotExistsException("Cannot delete, user not exists");
        }
        db.remove(user.getUsername());
    }


    @Override
    public Optional<User> findByUsername(String username) {
        if(db.containsValue(username)){
            return Optional.of(db.get(username));
        }
        return Optional.empty();
    }
}
