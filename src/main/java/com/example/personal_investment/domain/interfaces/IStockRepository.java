package com.example.personal_investment.domain.interfaces;

import com.example.personal_investment.domain.entities.stock.Stock;

import java.util.List;

public interface IStockRepository {
    void insert(Stock stock);
    void update(String id, Stock stock);
    Stock findByTicker(String ticker);
    List<Stock> findByCNPJ(String cnpj);
    List<Stock> findByCompanyName(String companyName);
}
