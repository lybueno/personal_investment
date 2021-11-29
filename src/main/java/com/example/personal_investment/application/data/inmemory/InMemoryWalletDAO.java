package com.example.personal_investment.application.data.inmemory;

import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.exceptions.EntityNotExistsException;
import com.example.personal_investment.domain.usecases.wallet.WalletDAO;

import java.util.*;

public class InMemoryWalletDAO implements WalletDAO {

    private static final Map<String, Wallet> db = new LinkedHashMap<>();

    @Override
    public String insert(Wallet wallet) {
        db.put(wallet.getId(), wallet);
        return wallet.getId();
    }

    @Override
    public Optional<Wallet> findOne(String id) {
        if (db.containsKey(id)) {
            return Optional.of(db.get(id));
        }
        return Optional.empty();
    }

    @Override
    public List<Wallet> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public void update(Wallet wallet) {
        if (!db.containsKey(wallet.getId())) {
            throw new EntityNotExistsException("Cannot update, wallet not exists");
        }
        db.replace(wallet.getId(), wallet);
    }

    @Override
    public void delete(Wallet wallet) {
        if (!db.containsKey(wallet.getId())) {
            throw new EntityNotExistsException("Cannot delete, wallet not exists");
        }
        db.remove(wallet.getId());
    }

    @Override
    public List<Investment> getIncome(Wallet wallet) {
        if (!db.containsKey(wallet.getId())) {
            throw new EntityNotExistsException("Cannot view income, wallet not exists");
        }
        return wallet.getMyInvestments();
    }

    @Override
    public List<Wallet> findAllByUser(User user) {
        List<Wallet> userWallets = new ArrayList<>();
        for(Wallet w : db.values()){
            if(w.getUser().equals(user)){
                userWallets.add(w);
            }
        }
        return userWallets;
    }
}
