package com.example.personal_investment.domain.interfaces;

import com.example.personal_investment.domain.entities.brokerage_note.BrokerageNote;
import com.example.personal_investment.domain.entities.stock.Stock;

import java.util.List;

// O mesmo que NotaNegociacaoDAO
public interface IBrokerageNoteRepository {
    void generateNote(BrokerageNote transaction);
    List<BrokerageNote> getAllNotes();
    BrokerageNote getNotesByStock(Stock stock);
}
