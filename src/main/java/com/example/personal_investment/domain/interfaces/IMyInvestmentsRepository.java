package com.example.personal_investment.domain.interfaces;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.entities.wallet.Wallet;

import java.util.List;

// O mesmo que PossuiAcaoDAO
public interface IMyInvestmentsRepository {
    public void savePurchaseStock(StockTransaction stock);
    public List<Stock> getAllStocksByTickerAndWallet(Wallet wallet, String ticker);
}
