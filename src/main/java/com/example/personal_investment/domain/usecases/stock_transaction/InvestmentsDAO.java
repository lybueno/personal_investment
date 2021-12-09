package com.example.personal_investment.domain.usecases.stock_transaction;

import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.utils.DAO;

import java.util.List;
import java.util.Optional;

public interface InvestmentsDAO extends DAO<Investment, String> {
   Optional<Investment> findOneByTickerAndWallet(String ticker, Wallet wallet);
   Optional<Investment> findById(String id);
   List<Investment> findAllByWallet(String walletId);
}
