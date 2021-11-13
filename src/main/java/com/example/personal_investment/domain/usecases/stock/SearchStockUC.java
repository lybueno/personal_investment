package com.example.personal_investment.domain.usecases.stock;

import com.example.personal_investment.domain.entities.stock.Stock;

import java.util.List;
import java.util.Optional;

public class SearchStockUC {
    private final StockDAO stockDAO;

    public SearchStockUC(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }

    public Optional<Stock> findByTicker(String ticker){
        if(ticker == null){
            throw new IllegalArgumentException("Ticker cannot be null");
        }
        return stockDAO.findByTicker(ticker);
    }

    public List<Stock> findByCNPJ(String cnpj){
        if(cnpj == null){
            throw new IllegalArgumentException("Cnpj cannot be null");
        }
        return stockDAO.findByCNPJ(cnpj);
    }

    public List<Stock> findByCompanyName(String companyName){
        if(companyName == null){
            throw new IllegalArgumentException("Company name cannot be null");
        }
         return stockDAO.findByCompanyName(companyName);
    }
}
