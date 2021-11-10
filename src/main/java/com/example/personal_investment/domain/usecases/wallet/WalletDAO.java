package com.example.personal_investment.domain.usecases.wallet;

import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.utils.DAO;

public interface WalletDAO extends DAO<Wallet, String> {
}
