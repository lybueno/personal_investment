package com.example.personal_investment.domain.usecases.wallet;

import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.utils.Validator;

import java.util.List;

public class SearchWalletUC {

    private final WalletDAO walletDAO;

    public SearchWalletUC(WalletDAO walletDAO) {
        this.walletDAO = walletDAO;
    }

    public List<Wallet> findAll(){
        return walletDAO.findAll();
    }

    public List<Wallet> findWalleyByUser(User user){
        Validator.validateUser(user);
        return walletDAO.findAllByUser(user);
    }
}
