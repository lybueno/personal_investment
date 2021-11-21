package com.example.personal_investment.application.data.dao;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.exceptions.EntityNotExistsException;
import com.example.personal_investment.domain.usecases.stock.StockDAO;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryStockDAO implements StockDAO {

    private static final Map<String, Stock> db = new LinkedHashMap<>();

    @Override
    public String insert(Stock stock) {
        db.put(stock.getId(),stock);
        return stock.getId();
    }

    @Override
    public void update(Stock stock) {
        if(!db.containsKey(stock.getId()))
            throw new EntityNotExistsException("Cannot update, stock not exists");
        db.replace(stock.getId(),stock);
    }

    @Override
    public void delete(Stock stock) {
        if(!db.containsKey(stock.getId()))
            throw new EntityNotExistsException("Cannot delete, stock not exists");
        db.remove(stock.getId());
    }

    @Override
    public Optional<Stock> findById(String id) {
        return db.values().stream().filter(stock -> stock.getId().equals(id)).findAny();
    }

    @Override
    public Optional<Stock> findByTicker(String ticker) {
        return db.values().stream().filter(stock -> stock.getTicker().equals(ticker)).findAny();
    }

    @Override
    public List<Stock> findByCNPJ(String cnpj) {
        return db.values().stream().filter(
                stock -> stock.getCnpj().equals(cnpj)
        ).collect(Collectors.toList());
    }

    @Override
    public List<Stock> findByCompanyName(String name) {
        return db.values().stream().filter(
                stock -> stock.getCompanyName().equals(name)
        ).collect(Collectors.toList());
    }

    @Override
    public Optional<Stock> findOne(Stock stock) {
        if(db.containsKey(stock.getId()))
            return Optional.of(db.get(stock.getId()));
        return Optional.empty();
    }

    @Override
    public List<Stock> findAll() {
        return new ArrayList<>(db.values());
    }

}
