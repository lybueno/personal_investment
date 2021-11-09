package com.example.personal_investment.domain.usecases.user;

import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.utils.Validator;

public class CreateUserUC {

    private UserDAO userDAO;

    public CreateUserUC(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public void insert(User user){
        Validator.validateUser(user);
        userDAO.create(user);
    }
}
