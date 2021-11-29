package com.example.personal_investment.domain.entities.stock;

import com.example.personal_investment.domain.entities.tax.*;

import java.util.Arrays;

public enum StockType {
    REGULAR {
        @Override
        public Tax getTax() {
            return new RegularTax();
        }
    },
    BDR {
        @Override
        public Tax getTax() {
            return new BDRTax();
        }
    },
    FII {
        @Override
        public Tax getTax() {
            return new FIITax();
        }
    },
    ETF_REAL_ESTATE {
        @Override
        public Tax getTax() {
            return new ETFRealEstateTax();
        }
    },
    ETF_GENERAL {
        @Override
        public Tax getTax() {
            return new ETFGeneralTax();
        }
    };

    public static StockType toEnum(String value) {
        return Arrays.stream(StockType.values())
                .filter(c -> value.equals(c.toString()))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public abstract Tax getTax();
}
