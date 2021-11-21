package com.example.personal_investment.domain.usecases.user;

import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.utils.DAO;

public interface UserDAO extends DAO<User, String> {
}