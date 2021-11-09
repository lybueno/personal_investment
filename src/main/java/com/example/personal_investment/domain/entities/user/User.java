package com.example.personal_investment.domain.entities.user;

import com.example.personal_investment.domain.entities.wallet.Wallet;

import java.util.List;

public class User {
    private final String username;
    private final String password;
    private List<Wallet> wallets;

    public User(String username, String password, List<Wallet> wallets) {
        this.username = username;
        this.password = password;
        this.wallets = wallets;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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

    public String getPassword() {
        return password;
    }
}
