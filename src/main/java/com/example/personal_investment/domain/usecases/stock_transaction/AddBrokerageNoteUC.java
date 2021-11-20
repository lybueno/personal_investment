package com.example.personal_investment.domain.usecases.stock_transaction;

import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.exceptions.EntityAlreadyExistsException;
import com.example.personal_investment.domain.utils.Validator;

public class AddBrokerageNoteUC {
    private final BrokerageNoteDAO brokerageNoteDAO;

    public AddBrokerageNoteUC(BrokerageNoteDAO brokerageNoteDAO) {
        this.brokerageNoteDAO = brokerageNoteDAO;
    }


    public void add(StockTransaction stockTransaction) {

        Validator.validateTransaction(stockTransaction);

        String id = stockTransaction.getId();

        if (brokerageNoteDAO.findById(id).isPresent())
            throw new EntityAlreadyExistsException("This id is already registered");

        brokerageNoteDAO.insert(stockTransaction);
    }

}
