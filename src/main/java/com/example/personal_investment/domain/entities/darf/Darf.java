package com.example.personal_investment.domain.entities.darf;

import com.example.personal_investment.domain.entities.stock.StockType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Darf {
    private final StockType stockType;
    private final LocalDate dueDate;
    private final BigDecimal taxAmount;
    private final BigDecimal saleValue;
    private final BigDecimal averagePurchaseValue;

    public Darf(BigDecimal taxAmount, StockType stockType, LocalDate dueDate, BigDecimal saleValue, BigDecimal averagePurchaseValue) {
        this.taxAmount = taxAmount;
        this.stockType = stockType;
        this.dueDate = dueDate;
        this.saleValue = saleValue;
        this.averagePurchaseValue = averagePurchaseValue;
    }

    public BigDecimal getAveragePurchaseValue() {
        return averagePurchaseValue;
    }

    public StockType getStockType() {
        return stockType;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public BigDecimal getSaleValue() {
        return saleValue;
    }
}
