package com.example.personal_investment.domain.usecases;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.interfaces.IStockRepository;

import java.util.List;

public class SearchStockUC {
    private final IStockRepository stockRepository;

    public SearchStockUC(IStockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock findByTicker(String ticker){
        if(ticker == null){
            throw new IllegalArgumentException("Ticker cannot be null");
        }
        return stockRepository.findByTicker(ticker);
    }

    public List<Stock> findByCNPJ(String cnpj){
        if(cnpj == null){
            throw new IllegalArgumentException("Cnpj cannot be null");
        }
        return stockRepository.findByCNPJ(cnpj);
    }

    public List<Stock> findByCompanyName(String companyName){
        if(companyName == null){
            throw new IllegalArgumentException("Company name cannot be null");
        }
         return stockRepository.findByCompanyName(companyName);
    }
}
