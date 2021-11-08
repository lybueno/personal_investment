package com.example.personal_investment.domain.usecases;


import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.interfaces.IWalletRepository;

public class AddWalletUC {
    private final IWalletRepository walletRepository;

    public AddWalletUC(IWalletRepository walletRepository){
        this.walletRepository = walletRepository;
    }

    public void insert(Wallet wallet){

    }
}
