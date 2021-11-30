package com.example.personal_investment.domain.usecases.stock_transaction;

import com.example.personal_investment.domain.entities.darf.Darf;

import java.util.List;

public class SearchDarfUC {

    private final DarfDAO darfDAO;

    public SearchDarfUC(DarfDAO darfDAO) {
        this.darfDAO = darfDAO;
    }

    public List<Darf> findAll(){
        return darfDAO.findAll();
    }
}
