package com.example.personal_investment.domain.usecases.wallet;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.usecases.stock_transaction.BrokerageNoteDAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class CalculateStockIncomeUC {

    private final WalletDAO walletDAO;
    private final BrokerageNoteDAO brokerageNoteDAO;

    public CalculateStockIncomeUC(WalletDAO walletDAO, BrokerageNoteDAO brokerageNoteDAO) {
        this.walletDAO = walletDAO;
        this.brokerageNoteDAO = brokerageNoteDAO;
    }

    public BigDecimal calculate(int time, BigDecimal currentValue){
        LocalDate today = LocalDate.now().withDayOfMonth(1);
        LocalDate initialDate = today.minusMonths(time);
        LocalDate endDate = initialDate.withDayOfMonth(initialDate.lengthOfMonth());
        List<StockTransaction> transactions = brokerageNoteDAO.findTransactionsBetween(initialDate, endDate);
        if(transactions.isEmpty()){
            return null;
        }
        BigDecimal lastValue = transactions.get(1).getUnitaryValue();
        BigDecimal income = lastValue.divide(currentValue);
        return income;
    }
}
