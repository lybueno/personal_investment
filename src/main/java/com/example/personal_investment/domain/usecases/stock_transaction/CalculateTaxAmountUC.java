package com.example.personal_investment.domain.usecases.stock_transaction;

import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.entities.tax.*;
import com.example.personal_investment.domain.exceptions.EntityNotExistsException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CalculateTaxAmountUC {

    private final BrokerageNoteDAO brokerageNoteDAO;
    private final InvestmentsDAO investmentsDAO;
    private final String limitExemptionToStockRegular = "20000.00";
    private final CalculateTax calculateTax = new CalculateTax();

    public CalculateTaxAmountUC(BrokerageNoteDAO brokerageNoteDAO, InvestmentsDAO investmentsDAO) {
        this.brokerageNoteDAO = brokerageNoteDAO;
        this.investmentsDAO = investmentsDAO;
    }

    public BigDecimal calculate(StockTransaction transaction) {
        Investment investment = investmentsDAO.findOneByTicker(transaction.getStock().getTicker()).orElseThrow(
                () -> new EntityNotExistsException("Investment not exists, cannot calculate tax amount")
        );

        BigDecimal profit = calculateProfit(investment, transaction);

        if (profit.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        switch (transaction.getStock().getType()) {
            case REGULAR: {
                BigDecimal limitExemption = new BigDecimal(limitExemptionToStockRegular);
                BigDecimal totalSaleMonth = getTotalSaleMonth();
                if (totalSaleMonth.compareTo(limitExemption) <= 0) {
                    return BigDecimal.ZERO;
                }
                return calculateTax(profit, new RegularTax());
            }
            case BDR: {
                return calculateTax(profit, new BDRTax());
            }
            case FII: {
                return calculateTax(profit, new FIITax());
            }
            case ETF_GENERAL: {
                return calculateTax(profit, new ETFGeneralTax());
            }
            case ETF_REAL_ESTATE: {
                return calculateTax(profit, new ETFRealEstateTax());
            }
            default:
                return BigDecimal.ZERO;
        }
    }

    private BigDecimal calculateTax(BigDecimal profit, Tax tax) {
        calculateTax.setTax(tax);
        return calculateTax.calculate(profit);
    }

    private BigDecimal calculateProfit(Investment investment, StockTransaction transaction) {
        BigDecimal profitOrLoss = transaction.getUnitaryValue().subtract(investment.calculateAverageValue());
        return profitOrLoss.multiply(BigDecimal.valueOf(transaction.getQuantity()));
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