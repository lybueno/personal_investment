package com.example.personal_investment.domain.usecases.user;

import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.exceptions.EntityAlreadyExistsException;
import com.example.personal_investment.domain.exceptions.IncorrectPasswordException;

public class RegisterUserUC {

    private final UserDAO userDAO;

    public RegisterUserUC(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User signUp(String username, String password, String confirmPassword) {
        User user = new User(username, password);
        if (userDAO.findByUsername(username).isPresent()) {
            throw new EntityAlreadyExistsException("This username is already registered");
        }
        if (!password.equals(confirmPassword)) {
            throw new IncorrectPasswordException("Password does not match with confirmPassword");
        }

        userDAO.insert(user);
        return user;
    }
}

