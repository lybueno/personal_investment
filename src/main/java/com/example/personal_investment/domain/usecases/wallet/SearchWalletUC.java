package com.example.personal_investment.domain.usecases.wallet;

import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.utils.Validator;

import java.util.List;
import java.util.Optional;

public class SearchWalletUC {

    private final WalletDAO walletDAO;

    public SearchWalletUC(WalletDAO walletDAO) {
        this.walletDAO = walletDAO;
    }

    public List<Wallet> findAll(){
        return walletDAO.findAll();
    }

    public List<Wallet> findWalletByUser(User user){
        Validator.validateUser(user);
        return walletDAO.findAllByUser(user);
    }

    public Optional<Wallet> findById(String id) {
        if(id == null){
            throw new IllegalArgumentException("Wallet ID cannot be null");
        }
        return walletDAO.findOne(id);
    }
    
}
