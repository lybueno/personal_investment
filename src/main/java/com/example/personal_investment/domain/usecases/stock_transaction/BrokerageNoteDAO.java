package com.example.personal_investment.domain.usecases.stock_transaction;

import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.utils.DAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BrokerageNoteDAO extends DAO<StockTransaction, String> {
    Optional<StockTransaction> findById(String id);
    Optional<StockTransaction> findByStock(String ticker);
    List<StockTransaction> findTransactionsBetween(LocalDate initialDate, LocalDate finalDate);
    List<StockTransaction> findAllByWallet(String id);
}
