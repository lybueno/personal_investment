package com.example.personal_investment.domain.usecases.stock_transaction;

import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.exceptions.EntityAlreadyExistsException;
import com.example.personal_investment.domain.utils.Validator;

public class AddInvestmentUC {
    private final InvestmentsDAO investmentsDAO;

    public AddInvestmentUC(InvestmentsDAO investmentsDAO) {
        this.investmentsDAO = investmentsDAO;
    }


    public void add(Investment Investment) {

        Validator.validateInvestment(Investment);

        String id = Investment.getId();

        if (investmentsDAO.findById(id).isPresent())
            throw new EntityAlreadyExistsException("This id is already registered");

        investmentsDAO.insert(Investment);
    }

}
