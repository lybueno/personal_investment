package com.example.personal_investment.domain.usecases;

import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.interfaces.IWalletRepository;
import com.example.personal_investment.domain.utils.Validator;

public class DeleteWalletUC {
    private final IWalletRepository walletRepository;

    public DeleteWalletUC(IWalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public void delete(String id, Wallet wallet){
        Validator.validateWallet(wallet);
        walletRepository.delete(id, wallet);
    }
}
