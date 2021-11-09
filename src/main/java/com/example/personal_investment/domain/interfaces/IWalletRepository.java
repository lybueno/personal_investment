package com.example.personal_investment.domain.interfaces;

import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.entities.wallet.Wallet;

public interface IWalletRepository {
    void insert(User user, Wallet wallet);
    void update(String id, Wallet wallet);
    void delete(String id, Wallet wallet);
}
