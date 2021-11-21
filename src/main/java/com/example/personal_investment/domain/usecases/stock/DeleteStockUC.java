package com.example.personal_investment.domain.usecases.stock;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.utils.Validator;

public class DeleteStockUC {
   private final StockDAO stockDAO;

   public DeleteStockUC(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
   }

    public void delete(Stock stock) {
        Validator.validateStock(stock);
        stockDAO.delete(stock);
    }

    public void deleteByTicker(String ticker) {
        if(ticker == null){
            throw new IllegalArgumentException("Ticker cannot be null");
        }
   }

    public void deleteById(String id) {
      if(id == null){
            throw new IllegalArgumentException("Id cannot be null");
       }
    }
}
