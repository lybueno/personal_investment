package com.example.personal_investment.domain.entities.tax;

import java.math.BigDecimal;

public class CalculateTax {
    private Tax strategy;

    public void setStrategy(Tax strategy) {
        this.strategy = strategy;
    }

    public BigDecimal calculate(BigDecimal profit) {
        return strategy.calculateTaxAmount(profit);
    }
}
