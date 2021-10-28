package com.example.personal_investment.domain.entities.wallet;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock.StockType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Wallet {
    private final String id;
    private String name;
    private StockType type;
    private final List<Stock> stocks;

    public Wallet(String id, String name, StockType type, List<Stock> stocks) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.stocks = stocks;
    }
    public Wallet(String id, String name, StockType type) {
        this(id, name, type, new ArrayList<>());
    }
    public Wallet(String name, StockType type) {
        this(UUID.randomUUID().toString(), name, type);
    }

    public void addPurchasedStocks(Stock stock) {
        stocks.add(stock);
    }

    public void removeSoldStocks(Stock stock) {
        stocks.remove(stock);
    }

    public void removeSoldStocksByTicker(String ticker) {
        Optional<Stock> stock = stocks.stream()
                .filter(s ->
                        s.getTicker().equals(ticker)
                )
                .findFirst();
        stock.ifPresent(s -> stocks.remove(s));
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

    public void setType(StockType type) {
        this.type = type;
    }
}
