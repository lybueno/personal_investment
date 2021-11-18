package com.example.personal_investment.domain.usecases.wallet;

import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.utils.Validator;

import java.util.List;

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

        return walletDAO.getIncome(wallet);
    }
}
