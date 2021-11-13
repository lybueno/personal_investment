package com.example.personal_investment.domain.entities.investment;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.wallet.Wallet;

import java.math.BigDecimal;

public class Investment {
    private final Wallet wallet;
    private final Stock stock;
    private Integer quantity;
    private BigDecimal averageValue;

    public Investment(Wallet wallet, Stock stock, Integer quantity, BigDecimal averageValue) {
        this.wallet = wallet;
        this.stock = stock;
        this.quantity = quantity;
        this.averageValue = averageValue;
    }

    public void decrementQuantity(Integer value) {
        if (value < quantity) {
            quantity -= value;
        }
    }

    public void incrementQuantity(Integer value) {
        quantity += value;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public Stock getStock() {
        return stock;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAverageValue() {
        return averageValue;
    }

    public void setAverageValue(BigDecimal averageValue) {
        this.averageValue = averageValue;
    }
}
