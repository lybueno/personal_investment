package com.example.personal_investment.domain.utils;

import java.util.List;
import java.util.Optional;

public interface DAO<T, K> {

    K create(T type);

    Optional<T> findOne(T type);

    Optional<T> findOneByKey(K key);

    List<T> findAll();

    boolean update(T type);

    boolean deleteByKey(K key);

    boolean delete(T type);
}
