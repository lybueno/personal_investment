package com.example.personal_investment.domain.entities.tax;

import java.math.BigDecimal;

public class CalculateTax {
    private Tax tax;

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public BigDecimal calculate(BigDecimal profit) {
        return tax.calculateTaxAmount(profit);
    }
}
