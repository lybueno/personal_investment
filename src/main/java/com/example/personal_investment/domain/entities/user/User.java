package com.example.personal_investment.domain.entities.user;

import com.example.personal_investment.domain.entities.wallet.Wallet;

import java.util.List;

public class User {
    private final String username;
    private List<Wallet> wallets;

    public User(String username, List<Wallet> wallets) {
        this.username = username;
        this.wallets = wallets;
    }

    public void addWallet(Wallet wallet){
        wallets.add(wallet);
    }

    public String getUsername() {
        return username;
    }

    public List<Wallet> getWallets() {
        return wallets;
    }

    public void setWallets(List<Wallet> wallets) {
        this.wallets = wallets;
    }
}
