package com.example.personal_investment.domain.usecases.stock;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.utils.Validator;

public class UpdateStockUC {
    private final StockDAO stockDAO;

    public UpdateStockUC(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }


    public void update(Stock stock){
        Validator.validateStock(stock);
        stockDAO.update(stock);
    }
}
