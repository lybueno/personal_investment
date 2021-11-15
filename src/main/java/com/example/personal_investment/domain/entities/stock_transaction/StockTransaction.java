package com.example.personal_investment.domain.entities.stock_transaction;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.wallet.Wallet;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class StockTransaction {
    private final Stock stock;
    private final Wallet wallet;
    private final LocalDate transactionDate;
    private final Integer quantity;
    private final BigDecimal unitaryValue;
    private final TransactionType transactionType;

    public StockTransaction(Stock stock, Wallet wallet, LocalDate transactionDate, Integer quantity, BigDecimal unitaryValue, TransactionType transactionType) {
        this.stock = stock;
        this.wallet = wallet;
        this.transactionDate = transactionDate;
        this.quantity = quantity;
        this.unitaryValue = unitaryValue;
        this.transactionType = transactionType;
    }

    public BigDecimal calculateTransactionAmount() {
        return unitaryValue.multiply(BigDecimal.valueOf(quantity));
    }

    public Stock getStock() {
        return stock;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitaryValue() {
        return unitaryValue;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockTransaction that = (StockTransaction) o;
        return stock.equals(that.stock) && wallet.equals(that.wallet) && transactionDate.equals(that.transactionDate) && quantity.equals(that.quantity) && unitaryValue.equals(that.unitaryValue) && transactionType == that.transactionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stock, wallet, transactionDate, quantity, unitaryValue, transactionType);
    }

    @Override
    public String toString() {
        return "StockTransaction{" +
                "stock=" + stock +
                ", wallet=" + wallet +
                ", transactionDate=" + transactionDate +
                ", quantity=" + quantity +
                ", unitaryValue=" + unitaryValue +
                ", transactionType=" + transactionType +
                '}';
    }
}
