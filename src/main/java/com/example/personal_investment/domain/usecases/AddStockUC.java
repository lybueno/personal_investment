package com.example.personal_investment.domain.usecases;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.interfaces.IStockRepository;
import com.example.personal_investment.domain.utils.ValidatorStock;

public class AddStockUC {
    private final IStockRepository stockRepository;

    public AddStockUC(IStockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void update(Stock stock) {
        ValidatorStock.validate(stock);
        stockRepository.insert(stock);
    }

}