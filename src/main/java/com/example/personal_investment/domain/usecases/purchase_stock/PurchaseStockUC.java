package com.example.personal_investment.domain.usecases.purchase_stock;

import com.example.personal_investment.domain.interfaces.IBrokerageNoteRepository;
import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.exceptions.TypeNotMatchException;
import com.example.personal_investment.domain.interfaces.IMyInvestmentsRepository;

public class PurchaseStockUC {
    private final IBrokerageNoteRepository brokerageNoteRepository;
    private final IMyInvestmentsRepository myInvestmentsRepository;

    public PurchaseStockUC(IBrokerageNoteRepository brokerageNoteRepository, IMyInvestmentsRepository myInvestmentsRepository) {
        this.brokerageNoteRepository = brokerageNoteRepository;
        this.myInvestmentsRepository = myInvestmentsRepository;
    }

    public void registerPurchase(StockTransaction transaction) {
        validatorTransaction(transaction);

        brokerageNoteRepository.generateNote(transaction);

        myInvestmentsRepository.savePurchaseStock(transaction);
    }

    private void validatorTransaction(StockTransaction transaction) {
        if(transaction.getStock() == null){
            throw new IllegalArgumentException("Stock cannot be null");
        }
        if(transaction.getWallet() == null){
            throw new IllegalArgumentException("Wallet cannot be null");
        }
        if(transaction.getTransactionDate() == null){
            throw new IllegalArgumentException("Transaction Date cannot be null");
        }
        if (transaction.getUnitaryValue() == null) {
            throw new IllegalArgumentException("Stock unitary value cannot be null");
        }
        if (transaction.getTransactionType() == null) {
            throw new IllegalArgumentException("Transaction Type cannot be null");
        }

        if(transaction.getStock().getType() != transaction.getWallet().getType()){
            throw new TypeNotMatchException();
        }
    }
}
