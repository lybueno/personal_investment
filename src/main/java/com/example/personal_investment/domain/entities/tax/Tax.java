package com.example.personal_investment.domain.entities.tax;

import java.math.BigDecimal;

public interface Tax {
    BigDecimal calculateTaxAmount(BigDecimal profit);
}
