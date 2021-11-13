package com.example.personal_investment.domain.usecases.stock;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.exceptions.EntityAlreadyExistsException;
import com.example.personal_investment.domain.utils.Validator;

public class AddStockUC {
    private final StockDAO stockDAO;

    public AddStockUC(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }


    public void add(Stock stock) {

        Validator.validateStock(stock);

        String ticker = stock.getTicker();
        String cnpj = stock.getCnpj();
        String companyName = stock.getCompanyName();
        String id = stock.getId();

        if (stockDAO.findById(id).isPresent())
            throw new EntityAlreadyExistsException("This id is already registered");
        if (stockDAO.findByTicker(ticker).isPresent())
            throw new EntityAlreadyExistsException("This ticker is already registered");
        if (stockDAO.findByCNPJ(cnpj).isPresent())
            throw new EntityAlreadyExistsException("This cnpj is already registered");
        if (stockDAO.findByCompanyName(companyName).isPresent())
            throw new EntityAlreadyExistsException("This company name is already registered");

        stockDAO.insert(stock);
    }

}
