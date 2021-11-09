package com.example.personal_investment.domain.usecases.wallet;

import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.interfaces.IWalletRepository;
import com.example.personal_investment.domain.utils.Validator;

public class AddWalletUC {
    private final IWalletRepository walletRepository;

    public AddWalletUC(IWalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public void insert(User user, Wallet wallet) {
        Validator.validateWallet(wallet);
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (user.getUsername() == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }
        walletRepository.insert(user, wallet);
    }
}
