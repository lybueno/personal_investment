package com.example.personal_investment.domain.usecases.stock_transaction;

import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.transaction.Transaction;
import com.example.personal_investment.domain.exceptions.AmountNotAllowed;

import java.math.BigDecimal;

public class RegisterSharePurchaseUC {

    private TransactionDAO transactionDAO;

    public RegisterSharePurchaseUC(TransactionDAO transactionDAO){
        this.transactionDAO = transactionDAO;
    }

    public void add(Transaction transaction){
        BigDecimal quantity = transaction.getQuantity();
        if(quantity.equals(0)){
            throw new AmountNotAllowed("Quantity cannot be null");
        }
        BigDecimal amount = transaction.getQuantity().multiply(transaction.getStock().getStockQuote());
        transaction.setAmount(amount);
        transactionDAO.insert(transaction);
    }

    /*
        TODO criar metodo para adicionar transacao de acao já em carteira para chamar o UC de
        calcular valor medio
     */
}
