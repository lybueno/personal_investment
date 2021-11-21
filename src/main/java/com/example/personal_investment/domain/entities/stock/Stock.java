package com.example.personal_investment.domain.entities.stock;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Stock {
    private final String id;
    private final StockType type;
    private String ticker;
    private String companyName;
    private String cnpj;
    private BigDecimal stockQuote;

    public Stock(String id, StockType type, String ticker, String companyName, String cnpj, BigDecimal stockQuote) {
        this.id = id;
        this.type = type;
        this.ticker = ticker;
        this.companyName = companyName;
        this.cnpj = cnpj;
        this.stockQuote = stockQuote;
    }

    public Stock(StockType type, String ticker, String companyName, String cnpj, BigDecimal stockQuote) {
        this(UUID.randomUUID().toString(),type,ticker,companyName,cnpj,stockQuote);
    }

    public String getId() {
        return id;
    }

    public StockType getType() {
        return type;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public BigDecimal getStockQuote() {
        return stockQuote;
    }

    public void setStockQuote(BigDecimal stockQuote) {
        this.stockQuote = stockQuote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return id.equals(stock.id) && type == stock.type && ticker.equals(stock.ticker) && companyName.equals(stock.companyName) && cnpj.equals(stock.cnpj) && stockQuote.equals(stock.stockQuote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, ticker, companyName, cnpj, stockQuote);
    }
}
