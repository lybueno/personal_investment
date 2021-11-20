package com.example.personal_investment.domain.entities.darf;

import com.example.personal_investment.domain.entities.stock.StockType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Darf {
    private final String id;
    private final StockType stockType;
    private final LocalDate dueDate;
    private final BigDecimal taxAmount;
    private final BigDecimal saleValue;
    private final BigDecimal averagePurchaseValue;

    public Darf(String id, StockType stockType, LocalDate dueDate, BigDecimal taxAmount, BigDecimal saleValue, BigDecimal averagePurchaseValue) {
        this.id = id;
        this.stockType = stockType;
        this.dueDate = dueDate;
        this.taxAmount = taxAmount;
        this.saleValue = saleValue;
        this.averagePurchaseValue = averagePurchaseValue;
    }

    public Darf(StockType stockType, LocalDate dueDate, BigDecimal taxAmount, BigDecimal saleValue, BigDecimal averagePurchaseValue) {
        this.id = UUID.randomUUID().toString();
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

    @Override
    public String toString() {
        return "Darf{" +
                "id='" + id + '\'' +
                ", stockType=" + stockType +
                ", dueDate=" + dueDate +
                ", taxAmount=" + taxAmount +
                ", saleValue=" + saleValue +
                ", averagePurchaseValue=" + averagePurchaseValue +
                '}';
    }
}
