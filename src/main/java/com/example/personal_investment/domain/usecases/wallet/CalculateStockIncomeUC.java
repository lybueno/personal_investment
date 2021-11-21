package com.example.personal_investment.domain.usecases.wallet;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.usecases.stock_transaction.BrokerageNoteDAO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class CalculateStockIncomeUC {

    private final WalletDAO walletDAO;
    private final BrokerageNoteDAO brokerageNoteDAO;

    public CalculateStockIncomeUC(WalletDAO walletDAO, BrokerageNoteDAO brokerageNoteDAO) {
        this.walletDAO = walletDAO;
        this.brokerageNoteDAO = brokerageNoteDAO;
    }

    public BigDecimal calculate(int time, BigDecimal currentValue, Stock stock){
        LocalDate today = LocalDate.now().withDayOfMonth(1);
        LocalDate initialDate = today.minusMonths(time);
        LocalDate endDate = initialDate.withDayOfMonth(initialDate.lengthOfMonth());
        List<StockTransaction> transactions = brokerageNoteDAO.findTransactionsBetween(initialDate, endDate);
        for (StockTransaction st: transactions) {
            if (st.getStock().equals(stock)){
                return ((currentValue.divide(stock.getStockQuote(), 2, RoundingMode.HALF_EVEN))
                        .subtract(new BigDecimal("1"))
                        .multiply(new BigDecimal("100")));
            }
        }
        return null;
    }
}
