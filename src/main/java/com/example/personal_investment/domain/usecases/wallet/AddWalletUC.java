package com.example.personal_investment.domain.usecases.wallet;

import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.interfaces.IWalletRepository;
import com.example.personal_investment.domain.utils.Validator;

public class AddWalletUC {
    private final WalletDAO walletDAO;

    public AddWalletUC(WalletDAO walletDAO) {
        this.walletDAO = walletDAO;
    }

    public void insert(User user, Wallet wallet) {
        Validator.validateWallet(wallet);

        walletDAO.create(wallet);
    }
}
