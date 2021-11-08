package com.example.personal_investment.domain.exceptions;

public class TypeNotMatchException extends RuntimeException{
    public TypeNotMatchException(String message){
        super(message);
    }
}
