package com.example.personal_investment.domain.entities.tax;

import java.math.BigDecimal;

public interface Tax {
    default BigDecimal calculateProfit(BigDecimal unitaryValue, BigDecimal averageValue, int quantity) {
        return (unitaryValue
                .subtract(averageValue))
                .multiply(BigDecimal.valueOf(quantity));
    }

    BigDecimal calculateTaxAmount(BigDecimal profit);
}
