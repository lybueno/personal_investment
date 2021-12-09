package com.example.personal_investment.domain.usecases.user;

import com.example.personal_investment.domain.entities.user.User;

import java.util.Optional;

public class FindUserUC {

    private final UserDAO userDAO;

    public FindUserUC(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Optional<User> findOneByUsername(String username) {
        if (username == null || username.isEmpty())
            throw new IllegalArgumentException("Username can not be null.");
        return userDAO.findByUsername(username);
    }

    public Optional<User> findOneById(String id){
        if (id == null || id.isEmpty())
            throw new IllegalArgumentException("ID can not be null.");
        return userDAO.findOne(id);
    }
}
