package com.example.personal_investment.application.data.inmemory;

import com.example.personal_investment.domain.entities.darf.Darf;
import com.example.personal_investment.domain.exceptions.EntityNotExistsException;
import com.example.personal_investment.domain.usecases.stock_transaction.DarfDAO;

import java.util.*;

public class InMemoryDarfDAO implements DarfDAO {

    private static final Map<String, Darf> db = new LinkedHashMap<>();

    @Override
    public String insert(Darf darf) {
        db.put(darf.getId(), darf);
        return darf.getId();
    }

    @Override
    public Optional<Darf> findOne(String key) {
        if(db.containsKey(key))
            return Optional.of(db.get(key));
        return Optional.empty();
    }

    @Override
    public List<Darf> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public void update(Darf darf) {
        if (!db.containsKey(darf.getId()))
            throw new EntityNotExistsException("Cannot update, darf not exists");
        db.replace(darf.getId(),darf);
    }

    @Override
    public void delete(Darf darf) {
        if(!db.containsKey(darf.getId()))
            throw new EntityNotExistsException("Cannot delete, darf not exists");
        db.remove(darf.getId());
    }
}
