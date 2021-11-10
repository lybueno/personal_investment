package com.example.personal_investment.domain.usecases.wallet;

import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.interfaces.IMyInvestmentsRepository;
import com.example.personal_investment.domain.utils.Validator;

public class DeleteWalletUC {
    private final WalletDAO walletDAO;
    private final IMyInvestmentsRepository myInvestmentsRepository;

    public DeleteWalletUC(WalletDAO walletDAO, IMyInvestmentsRepository myInvestmentsRepository1) {
        this.walletDAO = walletDAO;
        this.myInvestmentsRepository = myInvestmentsRepository1;
    }

    public void delete(String id, Wallet wallet) {
        if (id == null) {
            throw new IllegalArgumentException("Wallet id cannot be null");
        }
        Validator.validateWallet(wallet);

        if(myInvestmentsRepository.isWalletEmpty(wallet, id)){
            walletDAO.delete(wallet);
        } else{
            throw new RuntimeException("The wallet isn't empty");
        }
    }
}
