package com.example.personal_investment.domain.usecases.stock_transaction;

import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.exceptions.AmountNotAllowedException;
import com.example.personal_investment.domain.exceptions.EntityNotExistsException;
import com.example.personal_investment.domain.utils.Validator;

import java.math.BigDecimal;

public class RegisterStockSaleUC {
    private final InvestmentsDAO investmentsDAO;
    private final BrokerageNoteDAO brokerageNoteDAO;

    public RegisterStockSaleUC(InvestmentsDAO investmentsDAO, BrokerageNoteDAO brokerageNoteDAO) {
        this.investmentsDAO = investmentsDAO;
        this.brokerageNoteDAO = brokerageNoteDAO;
    }

    public void sell(StockTransaction transaction, BigDecimal tax) {
        Validator.validateTransaction(transaction);

        Investment investment = investmentsDAO.findOneByTicker(transaction.getStock().getTicker())
                .orElseThrow(() -> new EntityNotExistsException("Cannot sell, doesn`t have investments with this Stock"));

        if (investment.getQuantity() < transaction.getQuantity()) {
            throw new AmountNotAllowedException("Transaction quantity cannot be higher than investments quantity");
        }

        brokerageNoteDAO.insert(transaction);

        if (tax != null && tax.doubleValue() > 0.0) {
            // gerar darf
        }

        investment.decrementQuantity(transaction.getQuantity());
        investment.decrementAmount(transaction.calculateTransactionAmount());

        if (investment.getQuantity().equals(transaction.getQuantity())) {
            investmentsDAO.delete(investment);
        } else {
            investmentsDAO.update(investment);
        }
    }
}
