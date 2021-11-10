package com.example.personal_investment.application.data.dao;

import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.usecases.wallet.WalletDAO;

import java.util.*;

public class InMemoryWalletDAO implements WalletDAO {

    private static final Map<String, Wallet> db = new LinkedHashMap<>();

    //return id mesmo ou nome da carteira?
    @Override
    public String create(Wallet wallet) {
        db.put(wallet.getId(), wallet);
        return wallet.getId();
    }

    @Override
    public Optional<Wallet> findOne(Wallet wallet) {
        if(db.containsKey(wallet.getId())){
            return Optional.of(db.get(wallet.getId()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Wallet> findOneByKey(String id) {
        if(db.containsKey(id)){
            return Optional.of(db.get(id));
        }
        return Optional.empty();
    }

    @Override
    public List<Wallet> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean update(Wallet wallet) {
        if(db.containsKey(wallet.getId())){
            db.replace(wallet.getId(), wallet);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteByKey(String id) {
        if(db.containsKey(id)){
            db.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Wallet wallet) {
        return deleteByKey(wallet.getId());
    }

}
