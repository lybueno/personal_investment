package com.example.personal_investment.application.viewmodel;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock.StockType;

import java.math.BigDecimal;

public final class StockVM {
    private final String id;
    private final StockType type;
    private final String ticker;
    private final String companyName;
    private final String cnpj;
    private final String stockQuote;

    public StockVM(String id, StockType type, String ticker, String companyName, String cnpj, String stockQuote) {
        this.id = id;
        this.type = type;
        this.ticker = ticker;
        this.companyName = companyName;
        this.cnpj = cnpj;
        this.stockQuote = stockQuote;
    }

    public StockVM(Stock stock) {
        this.id = stock.getId();
        this.type = stock.getType();
        this.ticker = stock.getTicker();
        this.companyName = stock.getCompanyName();
        this.cnpj = stock.getCnpj();
        this.stockQuote = stock.getStockQuote().toString();
    }

    public Stock toStockEntity() {
        return new Stock(
                this.id,
                this.type,
                this.ticker,
                this.companyName,
                this.cnpj,
                new BigDecimal(this.stockQuote)
        );
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type.toString();
    }

    public String getTicker() {
        return ticker;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getStockQuote() {
        return stockQuote;
    }
}
