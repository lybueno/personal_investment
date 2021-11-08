package com.example.personal_investment.domain.entities.tax;

import com.example.personal_investment.domain.entities.stock.StockType;

import java.math.BigDecimal;

public interface Tax {
    public BigDecimal calculateTaxAmount(BigDecimal profit);
}
