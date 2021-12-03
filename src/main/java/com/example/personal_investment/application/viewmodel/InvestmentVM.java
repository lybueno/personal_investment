package com.example.personal_investment.application.viewmodel;

import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public final class InvestmentVM {

    private final String id;
    private final String ticker;
    private final String averageValue;
    private final String stockType;
    private final String quantity;

    public InvestmentVM(String id, String ticker, String averageValue, String stockType, String quantity) {
        this.id = id;
        this.ticker = ticker;
        this.averageValue = averageValue;
        this.stockType = stockType;
        this.quantity = quantity;
    }

    public InvestmentVM(Investment investment) {
        this.id = investment.getId();
        this.ticker = investment.getStock().getTicker();
        this.averageValue = investment.calculateAverageValue().toString();
        this.stockType = investment.getStock().getTicker();
        this.quantity = investment.getQuantity().toString();
    }

    public String getId() {
        return id;
    }

    public String getTicker() {
        return ticker;
    }

    public String getAverageValue() {
        return averageValue;
    }

    public String getStockType() {
        return stockType;
    }

    public String getQuantity() {
        return quantity;
    }
}
