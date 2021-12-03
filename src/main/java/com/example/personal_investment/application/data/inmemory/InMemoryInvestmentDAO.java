package com.example.personal_investment.application.data.inmemory;

import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.exceptions.EntityNotExistsException;
import com.example.personal_investment.domain.usecases.stock_transaction.InvestmentsDAO;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryInvestmentDAO implements InvestmentsDAO {

    private static final Map<String, Investment> db = new LinkedHashMap<>();

    @Override
    public String insert(Investment investment) {
        db.put(investment.getId(), investment);
        return investment.getId();
    }

    @Override
    public void update(Investment investment) {
        if (!db.containsKey(investment.getId()))
            throw new EntityNotExistsException("Cannot update, investment not exists");
        db.replace(investment.getId(),investment);
    }

    @Override
    public void delete(Investment investment) {
        if(!db.containsKey(investment.getId()))
            throw new EntityNotExistsException("Cannot delete, investment not exists");
        db.remove(investment.getId());
    }

    @Override
    public Optional<Investment> findOne(String key) {
        if(db.containsKey(key))
            return Optional.of(db.get(key));
        return Optional.empty();
    }

    @Override
    public List<Investment> findAll() {
        return new ArrayList<> (db.values());
    }

    @Override
    public Optional<Investment> findOneByTicker(String ticker) {
        return db.values().stream().filter(investment -> investment.getStock().getTicker().equals(ticker)).findAny();
    }

    @Override
    public Optional<Investment> findById(String id) {
        return db.values().stream().filter(investment -> investment.getId().equals(id)).findAny();
    }

    @Override
    public List<Investment> findAllByWallet(String walletId) {
        return db.values().stream().filter(investment -> investment.getWallet().getId().equals(walletId)).collect(Collectors.toList());
    }


}
