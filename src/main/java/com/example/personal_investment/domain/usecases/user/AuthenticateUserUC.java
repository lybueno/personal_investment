package com.example.personal_investment.domain.usecases.user;

import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.exceptions.EntityAlreadyExistsException;
import com.example.personal_investment.domain.utils.Validator;

import java.util.Optional;

public class AuthenticateUserUC {

    private final UserDAO userDAO;

    public AuthenticateUserUC(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public Optional<User> login(User user){
        Validator.validateUser(user);

        if(userDAO.findOneByKey(user.getUsername()).isEmpty()){
            throw new EntityAlreadyExistsException("This username is not registered");
        }

        return userDAO.findOne(user);
    }
}
