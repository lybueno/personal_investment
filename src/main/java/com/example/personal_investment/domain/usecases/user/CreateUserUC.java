package com.example.personal_investment.domain.usecases.user;

import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.exceptions.EntityAlreadyExistsException;
import com.example.personal_investment.domain.utils.Validator;

import java.util.Optional;

public class CreateUserUC {

    private final UserDAO userDAO;

    public CreateUserUC(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public Optional<User> signUp(User user){
        Validator.validateUser(user);

        if(userDAO.findByUsername(user.getUsername()).isPresent()){
            throw new EntityAlreadyExistsException("This username is already registered");
        }

        String username = userDAO.insert(user);
        return userDAO.findByUsername(username);
    }
}

