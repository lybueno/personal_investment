package com.example.personal_investment.domain.usecases.stock;

import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.exceptions.WalletIsNotEmptyException;
import com.example.personal_investment.domain.usecases.stock_transaction.InvestmentsDAO;
import com.example.personal_investment.domain.utils.Validator;

import java.util.List;
import java.util.Optional;

public class DeleteStockUC {
    private final StockDAO stockDAO;
    private final InvestmentsDAO investmentsDAO;

    public DeleteStockUC(StockDAO stockDAO, InvestmentsDAO investmentsDAO) {
        this.stockDAO = stockDAO;
        this.investmentsDAO = investmentsDAO;
    }

    public void delete(Stock stock) {
        deleteById(stock.getId());
    }

    public void deleteById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        List<Investment> investments = investmentsDAO.findAll();

        Optional<Investment> investment = investments.stream().filter(s -> s.getStock().getId().equals(id)).findFirst();

        if (investment.isPresent()) {
            throw new WalletIsNotEmptyException("Cannot delete stock as it has an investment with it");
        }

        stockDAO.deleteById(id);
    }
}
