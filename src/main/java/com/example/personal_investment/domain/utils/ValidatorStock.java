package com.example.personal_investment.domain.utils;

import com.example.personal_investment.domain.entities.stock.Stock;

public class ValidatorStock {
    public static void validate(Stock stock) {
        if(stock.getType() == null){
            throw new IllegalArgumentException("Stock can not be null");
        }
        if(stock.getTicker() == null){
            throw new IllegalArgumentException("Ticker cannot be null ");
        }
        if(stock.getCnpj() == null){
            throw new IllegalArgumentException("CNPJ cannot be null ");
        }
        if(stock.getCompanyName() == null){
            throw new IllegalArgumentException("Company name cannot be null ");
        }
    }
}
