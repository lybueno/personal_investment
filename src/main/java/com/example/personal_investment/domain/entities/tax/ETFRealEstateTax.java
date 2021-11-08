package com.example.personal_investment.domain.entities.tax;

import java.math.BigDecimal;

public abstract class ETFRealEstateTax implements Tax{

    private BigDecimal taxAliquot = new BigDecimal(0.2);

    @Override
    public BigDecimal calculateTaxAmount(BigDecimal profit){
        return taxAliquot.multiply(profit);
    }
}
