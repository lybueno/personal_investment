package com.example.personal_investment.domain.exceptions;

public class AmountNotAllowedException extends RuntimeException{

    public AmountNotAllowedException(String msg){
        super(msg);
    }
}
