package com.example.personal_investment.domain.exceptions;

public class AmountNotAllowed extends RuntimeException{

    public AmountNotAllowed(String msg){
        super(msg);
    }
}
