package com.example.personal_investment.domain.exceptions;

public class WalletIsNotEmptyException extends RuntimeException{
    public WalletIsNotEmptyException(String message) {
        super(message);
    }
}
