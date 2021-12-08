package com.example.personal_investment.domain.usecases.stock_transaction;

import com.example.personal_investment.domain.entities.darf.Darf;
import com.example.personal_investment.domain.utils.DAO;

import java.util.List;

public interface DarfDAO extends DAO<Darf,String> {
    List<Darf> findAllByUserName(String value);
}
