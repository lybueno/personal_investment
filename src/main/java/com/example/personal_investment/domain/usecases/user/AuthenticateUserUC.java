package com.example.personal_investment.domain.usecases.user;

import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.exceptions.EntityNotExistsException;
import com.example.personal_investment.domain.exceptions.IncorrectPasswordException;

import java.util.Optional;

public class AuthenticateUserUC {

    private final UserDAO userDAO;

    public AuthenticateUserUC(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public User login(String username, String password){
        Optional<User> optional = userDAO.findByUsername(username);
        if(optional.isEmpty()){
            throw new EntityNotExistsException("This username is not registered");
        }

        User user = optional.orElseThrow(() -> new RuntimeException("Something went wrong"));

        if(!user.getPassword().equals(password)){
            throw new IncorrectPasswordException("Cannot login, password incorrect");
        }

        return user;
    }
}
