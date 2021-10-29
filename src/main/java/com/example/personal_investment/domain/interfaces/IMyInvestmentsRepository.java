package com.example.personal_investment.domain.interfaces;

import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;

// O mesmo que PossuiAcaoDAO
public interface IMyInvestmentsRepository {
    public void savePurchaseStock(StockTransaction stock);
}
