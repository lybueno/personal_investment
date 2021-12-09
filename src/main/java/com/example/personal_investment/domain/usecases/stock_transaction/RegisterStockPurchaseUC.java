package com.example.personal_investment.domain.usecases.stock_transaction;

import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.utils.Validator;

import java.math.BigDecimal;
import java.util.Optional;

public class RegisterStockPurchaseUC {

    private final BrokerageNoteDAO brokerageNoteDAO;
    private final InvestmentsDAO investmentsDAO;

    public RegisterStockPurchaseUC(BrokerageNoteDAO brokerageNoteDAO, InvestmentsDAO investmentsDAO) {
        this.brokerageNoteDAO = brokerageNoteDAO;
        this.investmentsDAO = investmentsDAO;
    }

    public void purchase(StockTransaction transaction) {
        Validator.validateTransaction(transaction);

        brokerageNoteDAO.insert(transaction);

        Optional<Investment> optionalInvestment = investmentsDAO
                .findOneByTickerAndWallet(
                        transaction.getStock().getTicker(),
                        transaction.getWallet()
                );

        if (optionalInvestment.isPresent() && optionalInvestment.get().getWallet().getId().equals(transaction.getWallet().getId())) {
            Investment investment = optionalInvestment.get();
            BigDecimal amount = transaction.getUnitaryValue()
                    .multiply(
                            BigDecimal.valueOf(
                                    transaction.getQuantity()
                            )
                    );
            investment.incrementAmount(amount);
            investment.incrementQuantity(transaction.getQuantity());

            investmentsDAO.update(investment);
        } else {
            investmentsDAO.insert(new Investment(
                            transaction
                    )
            );
        }
    }
}
