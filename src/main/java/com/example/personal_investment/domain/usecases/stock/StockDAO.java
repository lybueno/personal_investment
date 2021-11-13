package com.example.personal_investment.domain.usecases.stock;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.utils.DAO;

import java.util.List;
import java.util.Optional;

public interface StockDAO extends DAO<Stock,String> {
    Optional<Stock> findByTicker(String ticker);
    Optional<Stock> findByCNPJ(String cnpj);
    Optional<Stock> findByCompanyName(String name);
}
