package com.example.personal_investment.domain.usecases.stock_transaction;

import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.exceptions.EntityNotExistsException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CalculateTaxAmountUC {

    private final BrokerageNoteDAO brokerageNoteDAO;
    private final InvestmentsDAO investmentsDAO;
    private final String limitExemptionToStockRegular = "20000.00";

    public CalculateTaxAmountUC(BrokerageNoteDAO brokerageNoteDAO, InvestmentsDAO investmentsDAO) {
        this.brokerageNoteDAO = brokerageNoteDAO;
        this.investmentsDAO = investmentsDAO;
    }

    public BigDecimal calculate(StockTransaction transaction) {
        Investment investment = investmentsDAO.findOneByTicker(transaction.getStock().getTicker()).orElseThrow(
                () -> new EntityNotExistsException("Investment not exists, cannot calculate tax amount")
        );

        StockType stockType = transaction.getStock().getType();
        BigDecimal profit = stockType.getTax().calculateProfit(
                transaction.getUnitaryValue(),
                investment.calculateAverageValue(),
                transaction.getQuantity()
        );

        if (profit.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        if (stockType.equals(StockType.REGULAR)) {
            BigDecimal limitExemption = new BigDecimal(limitExemptionToStockRegular);
            BigDecimal totalSaleMonth = getTotalSaleMonth();
            if (totalSaleMonth.compareTo(limitExemption) <= 0) {
                return BigDecimal.ZERO;
            }
        }

        return stockType.getTax().calculateTaxAmount(profit);
    }

    private BigDecimal getTotalSaleMonth() {
        List<StockTransaction> list = getStockTransactionsByDate();
        BigDecimal totalSaleMonth = new BigDecimal("0");
        for (StockTransaction sale : list) {
            totalSaleMonth = totalSaleMonth.add(sale.calculateTransactionAmount());
        }
        return totalSaleMonth;
    }

    private List<StockTransaction> getStockTransactionsByDate() {
        LocalDate today = LocalDate.now();
        LocalDate initial = LocalDate.of(today.getYear(), today.getMonthValue(), 1);
        return brokerageNoteDAO.findSalesBetween(initial, today);
    }
}