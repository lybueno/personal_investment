package com.example.personal_investment.domain.utils;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.user.User;
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

    public static void validateUser(User user){
        if(user == null){
            throw new IllegalArgumentException("User cannot be null.");
        }
        if(user.getUsername() == null){
            throw new IllegalArgumentException("User name cannot be null.");
        }
        // TODO: se usuario tiver atributo senha, verificar se null
        /* como vcs preferem fazer a validação do usuario e senha?
            colocamos senha como atributo de usuário? ou apenas processamos
            essa informação no banco?
         */
    }
}
