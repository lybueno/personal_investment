package com.example.personal_investment.application.data.dao;

import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.exceptions.EntityNotExistsException;
import com.example.personal_investment.domain.usecases.stock_transaction.BrokerageNoteDAO;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryStockTransactionDAO implements BrokerageNoteDAO {

    private static final Map<String, StockTransaction> db = new LinkedHashMap<>();

    @Override
    public String insert(StockTransaction stockTransaction) {
        db.put(stockTransaction.getId(),stockTransaction);
        return stockTransaction.getId();
    }

    @Override
    public void update(StockTransaction stockTransaction) {
        if(!db.containsKey(stockTransaction.getId()))
            throw new EntityNotExistsException("Cannot update, stock transaction not exists");
        db.replace(stockTransaction.getId(),stockTransaction);
    }

    @Override
    public void delete(StockTransaction stockTransaction) {
        if(!db.containsKey(stockTransaction.getId()))
            throw new EntityNotExistsException("Cannot delete, stock transaction not exists");
        db.remove(stockTransaction.getId());
    }

    @Override
    public Optional<StockTransaction> findById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<StockTransaction> findByStock(String ticker) {
        return db.values().stream().filter(stockTransaction -> stockTransaction.getStock().getTicker().equals(ticker)).findAny();
    }

    @Override
    public List<StockTransaction> findSalesBetween(LocalDate initialDate, LocalDate finalDate) {
        return db.values().stream().filter(
                stockTransaction ->
                stockTransaction.getTransactionDate().isAfter(initialDate) &&
                        stockTransaction.getTransactionDate().isBefore(finalDate)).collect(Collectors.toList());
    }

    @Override
    public Optional<StockTransaction> findOne(StockTransaction stockTransaction) {
        if(db.containsKey(stockTransaction.getId()))
            return Optional.of(db.get(stockTransaction.getId()));
        return Optional.empty();
    }

    @Override
    public List<StockTransaction> findAll()  {
        return new ArrayList<>(db.values());
    }


}
