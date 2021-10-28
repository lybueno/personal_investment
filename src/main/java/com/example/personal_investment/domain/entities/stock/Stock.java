package com.example.personal_investment.domain.entities.stock;

import java.math.BigDecimal;
import java.util.Objects;

public class Stock {
    public Stock(String ticker, String companyName, String cnpj, BigDecimal stockQuote, StockType type) {
        this.ticker = ticker;
        this.companyName = companyName;
        this.cnpj = cnpj;
        this.stockQuote = stockQuote;
        this.type = type;
    }

    private String ticker;
    private String companyName;
    private String cnpj;
    private BigDecimal stockQuote;
    private final StockType type;

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
        return ticker.equals(stock.ticker) && companyName.equals(stock.companyName) && cnpj.equals(stock.cnpj) && type == stock.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticker, companyName, cnpj, type);
    }
}
