package com.example.personal_investment.domain.entities.darf;

import com.example.personal_investment.domain.entities.stock.StockType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Darf {

    private String id;
    private final StockType stockType;
    private final LocalDate dueDate;
    private final BigDecimal taxAmount;
    private final BigDecimal saleValue;
    private final BigDecimal averagePurchaseValue;

    public Darf(String id, BigDecimal taxAmount, StockType stockType, LocalDate dueDate, BigDecimal saleValue,
                BigDecimal averagePurchaseValue) {
        this.id = id;
        this.taxAmount = taxAmount;
        this.stockType = stockType;
        this.dueDate = dueDate;
        this.saleValue = saleValue;
        this.averagePurchaseValue = averagePurchaseValue;
    }

    public Darf(StockType stockType, LocalDate dueDate, BigDecimal taxAmount, BigDecimal saleValue, BigDecimal averagePurchaseValue) {
        this.stockType = stockType;
        this.dueDate = dueDate;
        this.taxAmount = taxAmount;
        this.saleValue = saleValue;
        this.averagePurchaseValue = averagePurchaseValue;
    }

    public String getId() {
        return id;
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
