package com.example.personal_investment.application.viewmodel;

import com.example.personal_investment.domain.entities.darf.Darf;
import com.example.personal_investment.domain.entities.stock.StockType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DarfVM {
    private final String id;
    private final String stockType;
    private final String dueDate;
    private final String taxAmount;
    private final String saleValue;
    private final String averagePurchaseValue;

    public DarfVM(String id,
                  String stockType,
                  String dueDate,
                  String taxAmount,
                  String saleValue,
                  String averagePurchaseValue) {
        this.id = id;
        this.stockType = stockType;
        this.dueDate = dueDate;
        this.taxAmount = taxAmount;
        this.saleValue = saleValue;
        this.averagePurchaseValue = averagePurchaseValue;
    }

    public DarfVM(Darf darf){
        this.id = darf.getId();
        this.stockType = darf.getStockType().toString();
        this.dueDate = darf.getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.taxAmount = darf.getTaxAmount().toString();
        this.saleValue = darf.getSaleValue().toString();
        this.averagePurchaseValue = darf.getAveragePurchaseValue().toString();
    }

    public String getId() {
        return id;
    }

    public String getStockType() {
        return stockType;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public String getSaleValue() {
        return saleValue;
    }

    public String getAveragePurchaseValue() {
        return averagePurchaseValue;
    }

}
