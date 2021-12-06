package com.example.personal_investment.application.viewmodel;

import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.wallet.Wallet;

import java.math.BigDecimal;
import java.util.Optional;

public final class InvestmentVM {

    private final String id;
    private final String ticker;
    private final String averageValue;
    private final String stockType;
    private final String quantity;
    private final String totalAmount;

    public InvestmentVM(String id, String ticker, String averageValue, String stockType, String quantity, String totalAmount) {
        this.id = id;
        this.ticker = ticker;
        this.averageValue = averageValue;
        this.stockType = stockType;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }


    public InvestmentVM(Investment investment) {
        this.id = investment.getId();
        this.ticker = investment.getStock().getTicker();
        this.averageValue = investment.calculateAverageValue().toString();
        this.stockType = investment.getStock().getTicker();
        this.quantity = investment.getQuantity().toString();
        this.totalAmount = investment.getTotalAmount().toString();
    }

    public Investment toInvestmentEntity(Wallet wallet, Stock stock){
        return new Investment(
                this.id,
                wallet,
                stock,
                Integer.getInteger(this.quantity),
                new BigDecimal(this.totalAmount)
        );
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
    public String getTotalAmount() {
        return totalAmount;
    }
}
