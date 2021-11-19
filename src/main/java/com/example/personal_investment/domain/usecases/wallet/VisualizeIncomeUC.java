package com.example.personal_investment.domain.usecases.wallet;

import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.utils.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: Arrumar usecase
public class VisualizeIncomeUC {
    private final WalletDAO walletDAO;

    public VisualizeIncomeUC(WalletDAO walletDAO) {
        this.walletDAO = walletDAO;
    }

    public List<Investment> getIncome(Wallet wallet, Integer periodTime){
        if(periodTime == null){
            throw new IllegalArgumentException("Period of time cannot be null");
        }
        Validator.validateWallet(wallet);

        if(wallet.isEmpty()){
            throw new RuntimeException("Wallet is empty");
        }

        return walletDAO.getIncome(wallet);
    }
}