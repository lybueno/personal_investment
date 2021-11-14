package com.example.personal_investment.domain.entities.brokerage_note;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.wallet.Wallet;

import java.math.BigDecimal;
import java.time.LocalDate;

// nota de negociação não poderia ser criada a partir de um cadastro de compra ou venda?
public class BrokerageNote {
    private final Stock stock;
    private final Wallet wallet;
    private final LocalDate transactionDate;
    private final Integer quantity;
    private final BigDecimal unitaryValue;
    private final TransactionType transactionType;

    public BrokerageNote(Stock stock, Wallet wallet, LocalDate transactionDate, Integer quantity, BigDecimal unitaryValue, TransactionType transactionType) {
        this.stock = stock;
        this.wallet = wallet;
        this.transactionDate = transactionDate;
        this.quantity = quantity;
        this.unitaryValue = unitaryValue;
        this.transactionType = transactionType;
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
}
