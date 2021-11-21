package com.example.personal_investment.domain.usecases.wallet;

import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.utils.DAO;

import java.util.List;

public interface WalletDAO extends DAO<Wallet, String> {
    List<Investment> getIncome(Wallet wallet);
}
