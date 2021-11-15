package com.example.personal_investment.domain.entities.investment;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.entities.wallet.Wallet;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Investment {
    private final Wallet wallet;
    private final Stock stock;
    private Integer quantity;
    private BigDecimal totalAmount;

    public Investment(Wallet wallet, Stock stock, Integer quantity, BigDecimal totalAmount) {
        this.wallet = wallet;
        this.stock = stock;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    public Investment(StockTransaction transaction) {
        this(
                transaction.getWallet(),
                transaction.getStock(),
                transaction.getQuantity(),
                transaction.calculateTransactionAmount()
        );
    }

    public void decrementQuantity(Integer value) {
        if (value < quantity) {
            quantity -= value;
        }
    }

    public void incrementQuantity(Integer value) {
        quantity += value;
    }

    public void incrementAmount(BigDecimal value) {
        this.totalAmount = this.totalAmount.add(value);
    }

    public void decrementAmount(BigDecimal value) {
        this.totalAmount = this.totalAmount.add(value);
    }

    public BigDecimal calculateAverageValue() {
        return totalAmount.divide(BigDecimal.valueOf(quantity), RoundingMode.HALF_EVEN);
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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
}
