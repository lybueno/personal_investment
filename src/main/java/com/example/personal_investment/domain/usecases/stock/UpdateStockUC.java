package com.example.personal_investment.domain.usecases.stock;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.interfaces.IStockRepository;
import com.example.personal_investment.domain.utils.Validator;

public class UpdateStockUC {
    private final IStockRepository stockRepository;

    public UpdateStockUC(IStockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }
    
    public void update(Stock stock){
        Validator.validateStock(stock);
        stockRepository.update(stock.getId(), stock);
    }
}
