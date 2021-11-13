package com.example.personal_investment.domain.usecases.stock;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.utils.Validator;

public class AddStockUC {
    private final StockDAO stockDAO;

    public AddStockUC(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }


    public void add(Stock stock) {
        Validator.validateStock(stock);
        stockDAO.insert(stock);
    }

}
