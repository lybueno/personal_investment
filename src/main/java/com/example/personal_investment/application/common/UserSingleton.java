package com.example.personal_investment.application.common;

import com.example.personal_investment.domain.entities.user.User;

public class UserSingleton {
    private static UserLogged instance;

    private UserSingleton() {
    }

    public static synchronized UserLogged getInstance() {
        if(instance == null) {
            instance = new UserLogged();
        }
        return instance;
    }

    public static void login(User user) {
        getInstance().setUser(user);
    }

    public static void logout() {
        getInstance().setUser(null);
    }
}
