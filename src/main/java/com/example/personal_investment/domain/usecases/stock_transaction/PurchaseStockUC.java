package com.example.personal_investment.domain.usecases.stock_transaction;

import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.interfaces.IBrokerageNoteRepository;
import com.example.personal_investment.domain.interfaces.IMyInvestmentsRepository;
import com.example.personal_investment.domain.utils.Validator;

public class PurchaseStockUC {
    private final IBrokerageNoteRepository brokerageNoteRepository;
    private final IMyInvestmentsRepository myInvestmentsRepository;

    public PurchaseStockUC(IBrokerageNoteRepository brokerageNoteRepository, IMyInvestmentsRepository myInvestmentsRepository) {
        this.brokerageNoteRepository = brokerageNoteRepository;
        this.myInvestmentsRepository = myInvestmentsRepository;
    }

    public void registerPurchase(StockTransaction transaction) {
        Validator.validateTransaction(transaction);

        brokerageNoteRepository.generateNote(transaction);

        myInvestmentsRepository.addPurchaseStock(transaction);
    }
}
