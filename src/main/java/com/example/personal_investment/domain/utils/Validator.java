package com.example.personal_investment.domain.utils;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.wallet.Wallet;

public class Validator {
    public static void validateStock(Stock stock) {
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
    public static void validateWallet(Wallet wallet){
        if(wallet.getType() == null){
            throw new IllegalArgumentException("Wallet type cannot be null");
        }

        if(wallet.getName() == null){
            throw new IllegalArgumentException("Wallet name cannot be null");
        }
    }
}
