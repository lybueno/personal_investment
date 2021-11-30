package com.example.personal_investment.domain.usecases.stock_transaction;

import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;

import java.util.List;

public class SearchBrokerageNoteUC {
    private final BrokerageNoteDAO brokerageNoteDAO;

    public SearchBrokerageNoteUC(BrokerageNoteDAO brokerageNoteDAO) {
        this.brokerageNoteDAO = brokerageNoteDAO;
    }

    public List<StockTransaction> findAll(){
        return brokerageNoteDAO.findAll();
    }
}
