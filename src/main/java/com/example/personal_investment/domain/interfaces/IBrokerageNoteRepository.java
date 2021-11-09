package com.example.personal_investment.domain.interfaces;

import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.entities.stock.Stock;

import java.util.List;

// O mesmo que NotaNegociacaoDAO
public interface IBrokerageNoteRepository {
    void generateNote(StockTransaction transaction);
    List<StockTransaction> getAllNotes();
    StockTransaction getNotesByStock(Stock stock);
}
