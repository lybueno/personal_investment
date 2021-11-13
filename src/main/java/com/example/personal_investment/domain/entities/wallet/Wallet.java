package com.example.personal_investment.domain.entities.wallet;

import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.entities.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Wallet {
    private final String id;
    private final List<Investment> myInvestments;
    private final User user;
    private StockType type;
    private String name;

    public Wallet(String id, String name, StockType type, List<Investment> myInvestments, User user) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.myInvestments = myInvestments;
        this.user = user;
    }

    public Wallet(String id, String name, StockType type, User user) {
        this(id, name, type, new ArrayList<>(), user);
    }

    public Wallet(String name, StockType type, User user) {
        this(UUID.randomUUID().toString(), name, type, user);
    }

    public void addInvestment(Investment investment) {
        this.myInvestments.add(investment);
    }

    public void removeInvestment(Investment stock) {
        this.myInvestments.remove(stock);
    }

    public void removeInvestmentByTicker(String ticker) {
        Optional<Investment> investment = myInvestments.stream()
                .filter(s ->
                        s.getStock().getTicker().equals(ticker)
                )
                .findFirst();
        investment.ifPresent(myInvestments::remove);
    }

    public boolean isEmpty() {
        return myInvestments.isEmpty();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StockType getType() {
        return type;
    }

    // Somente altera tipo se tiver vazia
    public void setType(StockType type) {
        if (isEmpty()) {
            this.type = type;
        }
    }

    public User getUser() {
        return user;
    }
}
