package com.example.personal_investment.application.data.sql;

import com.example.personal_investment.domain.entities.darf.Darf;
import com.example.personal_investment.domain.usecases.stock_transaction.DarfDAO;

import java.util.List;
import java.util.Optional;

public class SqliteDarfDAO implements DarfDAO {
    @Override
    public String insert(Darf type) {
        return null;
    }

    @Override
    public Optional<Darf> findOne(String key) {
        return Optional.empty();
    }

    @Override
    public List<Darf> findAll() {
        return null;
    }

    @Override
    public void update(Darf type) {

    }

    @Override
    public void delete(Darf type) {

    }
}
