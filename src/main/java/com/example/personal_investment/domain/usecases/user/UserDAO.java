package com.example.personal_investment.domain.usecases.user;

import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.utils.DAO;

import java.util.Optional;

public interface UserDAO extends DAO<User, String> {
    Optional<User> findByUsername(String username);
}