package com.example.personal_investment.domain.interfaces;

import com.example.personal_investment.domain.entities.wallet.Wallet;

public interface IWalletRepository {
    public void insert(Wallet wallet);
    public void update(String id, Wallet wallet);
    public void delete(String id, Wallet wallet);
}
