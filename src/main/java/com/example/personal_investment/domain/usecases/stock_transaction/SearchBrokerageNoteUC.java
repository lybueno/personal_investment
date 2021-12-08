package com.example.personal_investment.domain.usecases.stock_transaction;

import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;

import java.util.Arrays;
import java.util.List;

public class SearchBrokerageNoteUC {
    private final BrokerageNoteDAO brokerageNoteDAO;

    public SearchBrokerageNoteUC(BrokerageNoteDAO brokerageNoteDAO) {
        this.brokerageNoteDAO = brokerageNoteDAO;
    }

    public List<StockTransaction> findAll(){
        return brokerageNoteDAO.findAll();
    }

    public List<StockTransaction> findAllByWallet(String id) {
        return brokerageNoteDAO.findAllByWallet(id);
    }

}
