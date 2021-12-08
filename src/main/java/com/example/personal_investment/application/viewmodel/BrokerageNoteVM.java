package com.example.personal_investment.application.viewmodel;

import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class BrokerageNoteVM {
    private final String ticker;
    private final String transactionAmount;
    private final LocalDate transactionDate;
    private final String quantity;
    private final String unitaryValue;
    private final String transactionType;
    private final String cnpj;
    private final String companyName;
    private final String nameUser;

    public BrokerageNoteVM(String ticker, String transactionAmount,
                           LocalDate transactionDate, String quantity,
                           String unitaryValue, String transactionType,
                           String cnpj, String companyName,
                           String nameUser) {
        this.ticker = ticker;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
        this.quantity = quantity;
        this.unitaryValue = unitaryValue;
        this.transactionType = transactionType;
        this.cnpj = cnpj;
        this.companyName = companyName;
        this.nameUser = nameUser;
    }

    public BrokerageNoteVM(StockTransaction stockTransaction) {
        this.ticker = stockTransaction.getStock().getTicker();
        this.transactionAmount = stockTransaction.calculateTransactionAmount().toString();
        this.transactionDate = stockTransaction.getTransactionDate();
        this.quantity = stockTransaction.getQuantity().toString();
        this.unitaryValue = stockTransaction.getUnitaryValue().toString();
        this.transactionType = stockTransaction.getTransactionType().toString();
        this.cnpj = stockTransaction.getStock().getCnpj();
        this.companyName = stockTransaction.getStock().getCompanyName();
        this.nameUser = "null";
    }

    public String getTicker() {
        return ticker;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public String getTransactionDate() {
        return transactionDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUnitaryValue() {
        return unitaryValue;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getNameUser() {
        return nameUser;
    }
}
