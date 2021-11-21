package com.example.personal_investment.domain.usecases.stock;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.utils.DAO;

import java.util.List;
import java.util.Optional;

public interface StockDAO extends DAO<Stock,String> {
    Optional<Stock> findById(String id);
    Optional<Stock> findByTicker(String ticker);
    List<Stock> findByCNPJ(String cnpj);
    List<Stock> findByCompanyName(String name);
}
