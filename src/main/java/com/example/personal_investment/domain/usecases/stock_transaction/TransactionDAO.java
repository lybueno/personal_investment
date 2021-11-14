package com.example.personal_investment.domain.usecases.stock_transaction;

import com.example.personal_investment.domain.entities.transaction.Transaction;
import com.example.personal_investment.domain.utils.DAO;

import java.time.LocalDate;
import java.util.Optional;

public interface TransactionDAO extends DAO<Transaction, String> {

    Optional<Transaction> findByStock(String ticker);
    Optional<Transaction> findByDate(LocalDate date);

}
