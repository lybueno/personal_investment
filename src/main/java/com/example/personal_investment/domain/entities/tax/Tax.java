package com.example.personal_investment.domain.entities.tax;

import java.math.BigDecimal;

public interface Tax {
    public BigDecimal calculateTaxAmount(BigDecimal profit);
}
