package com.example.personal_investment.domain.usecases.stock_transaction;

import com.example.personal_investment.domain.entities.darf.Darf;
import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.exceptions.AmountNotAllowedException;
import com.example.personal_investment.domain.exceptions.EntityNotExistsException;
import com.example.personal_investment.domain.utils.Validator;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RegisterStockSaleUC {
    private final InvestmentsDAO investmentsDAO;
    private final BrokerageNoteDAO brokerageNoteDAO;
    private final DarfDAO darfDAO;

    public RegisterStockSaleUC(InvestmentsDAO investmentsDAO, BrokerageNoteDAO brokerageNoteDAO, DarfDAO darfDAO) {
        this.investmentsDAO = investmentsDAO;
        this.brokerageNoteDAO = brokerageNoteDAO;
        this.darfDAO = darfDAO;
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
            LocalDate today = LocalDate.now();
            LocalDate dueDate = LocalDate.of(today.getYear(), today.getMonth().plus(1), today.lengthOfMonth());
            Darf darf = new Darf(transaction.getStock().getType(), dueDate, tax, transaction.getUnitaryValue(), investment.calculateAverageValue());
            darfDAO.insert(darf);
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
