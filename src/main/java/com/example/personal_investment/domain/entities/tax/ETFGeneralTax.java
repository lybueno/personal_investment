package com.example.personal_investment.domain.entities.tax;

import java.math.BigDecimal;

public abstract class ETFGeneralTax implements Tax {

    private BigDecimal taxAliquot = new BigDecimal(0.15);
    @Override
    public BigDecimal calculateTaxAmount(BigDecimal profit){
        return taxAliquot.multiply(profit);
    }
}
