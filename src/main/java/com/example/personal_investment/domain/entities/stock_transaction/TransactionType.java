package com.example.personal_investment.domain.entities.stock_transaction;

import java.util.Arrays;

public enum TransactionType {
    PURCHASE,
    SALE;

    public static TransactionType toEnum(String value) {
        return Arrays.stream(TransactionType.values())
                .filter(c -> value.equals(c.toString()))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
