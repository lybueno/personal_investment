package com.example.personal_investment.domain.usecases;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.interfaces.IMyInvestmentsRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class AverageValueStockUC {
    private final IMyInvestmentsRepository myInvestmentsRepository;

    public AverageValueStockUC(IMyInvestmentsRepository myInvestmentsRepository) {
        this.myInvestmentsRepository = myInvestmentsRepository;
    }

    public BigDecimal calculate(String ticker, Wallet wallet){
        if(ticker == null){
            throw new IllegalArgumentException("Ticker cannot be null");
        }

        List<Stock> stocks = myInvestmentsRepository.getAllStocksByTickerAndWallet(wallet ,ticker);

        int quantityStocks = stocks.size();
        BigDecimal div = new BigDecimal(Integer.toString(quantityStocks));
        BigDecimal sum = new BigDecimal("0");
        for (Stock stock : stocks) {
            sum = sum.add(stock.getStockQuote());
        }

        return sum.divide(div, RoundingMode.HALF_EVEN);
    }

}
