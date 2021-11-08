package com.example.personal_investment.domain.usecases;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.interfaces.IStockRepository;
import com.example.personal_investment.domain.utils.Validator;

public class AddStockUC {
    private final IStockRepository stockRepository;

    public AddStockUC(IStockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void update(Stock stock) {
        Validator.validateStock(stock);
        stockRepository.insert(stock);
    }

}
