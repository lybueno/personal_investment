package com.example.personal_investment.domain.usecases.user;

import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.utils.DAO;

import java.util.Optional;

// como preferem manipular o usuário, por nome de usuário?
public interface UserDAO extends DAO<User, String> {

    Optional<User> findByName(String name);
    Optional<User> authenticate(String name, String email);
}
