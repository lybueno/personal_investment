package com.example.personal_investment.domain.usecases.stock_transaction;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.interfaces.IMyInvestmentsRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class AverageStockPurchaseValue {
    private final IMyInvestmentsRepository myInvestmentsRepository;

    public AverageStockPurchaseValue(IMyInvestmentsRepository myInvestmentsRepository) {
        this.myInvestmentsRepository = myInvestmentsRepository;
    }

    public BigDecimal calculate(String ticker, Wallet wallet){
        if(ticker == null){
            throw new IllegalArgumentException("Ticker cannot be null");
        }
        if(wallet == null){
            throw new IllegalArgumentException("Wallet cannot be null");
        }

        List<Stock> myInvestments = myInvestmentsRepository.getAllStocksByTickerAndWallet(wallet ,ticker);
        
        BigDecimal quantityStocks = new BigDecimal(Integer.toString(myInvestments.size()));
        BigDecimal valueOfAllStocks = new BigDecimal("0");
        for (Stock stock : myInvestments) {
            valueOfAllStocks = valueOfAllStocks.add(stock.getStockQuote());
        }

        return valueOfAllStocks.divide(quantityStocks, RoundingMode.HALF_EVEN);
    }

}