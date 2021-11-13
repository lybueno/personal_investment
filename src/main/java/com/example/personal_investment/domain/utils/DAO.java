package com.example.personal_investment.domain.utils;

import java.util.List;
import java.util.Optional;

public interface DAO<T, K> {

    K insert(T type);

    Optional<T> findOne(T type);

    List<T> findAll();

    void update(T type);

    void delete(T type);
}
