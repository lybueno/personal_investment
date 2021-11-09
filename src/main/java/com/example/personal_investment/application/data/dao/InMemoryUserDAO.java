package com.example.personal_investment.application.data.dao;

import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.usecases.user.UserDAO;

import java.util.*;

public class InMemoryUserDAO implements UserDAO {

    private static final Map<String, User> db = new LinkedHashMap<>();

    @Override
    public String create(User user) {
        db.put(user.getUsername(), user);
        return user.getUsername();
    }

    @Override
    public Optional<User> findOne(User user) {
        if(db.containsKey(user.getUsername())){
            return Optional.of(db.get(user.getUsername()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findOneByKey(String key) {
        if(db.containsKey(key)){
            return Optional.of(db.get(key));
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean update(User user) {
        String id = user.getUsername();
        if(db.containsKey(id)){
            db.replace(id, user);
        }
        return false;
    }

    @Override
    public boolean deleteByKey(String key) {
        if(db.containsKey(key)){
            db.remove(key);
        }
        return false;
    }

    @Override
    public boolean delete(User user) {
        return deleteByKey(user.getUsername());
    }
}
