package com.example.personal_investment.domain.usecases.stock_transaction;

import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.utils.Validator;

import java.util.List;

public class SearchInvestmentUC {

    private InvestmentsDAO investmentsDAO;

    public SearchInvestmentUC(InvestmentsDAO investmentsDAO) {
        this.investmentsDAO = investmentsDAO;
    }

    public List<Investment> findAllInvestmentsByWallet(Wallet wallet){
        Validator.validateWallet(wallet);

        return investmentsDAO.findAllByWallet(wallet.getId());
    }
}
