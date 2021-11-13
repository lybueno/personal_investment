package com.example.personal_investment.domain.usecases.wallet;

import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.interfaces.IMyInvestmentsRepository;
import com.example.personal_investment.domain.utils.Validator;

public class UpdateWalletUC {
    private final WalletDAO walletDAO;
    private final IMyInvestmentsRepository myInvestmentsRepository; // trocar para MyInvestmentsDAO

    public UpdateWalletUC(WalletDAO walletDAO, IMyInvestmentsRepository myInvestmentsRepository) {
        this.walletDAO = walletDAO;
        this.myInvestmentsRepository = myInvestmentsRepository;
    }


    public void update(String id, Wallet wallet, StockType newType) {
        if (id == null) {
            throw new IllegalArgumentException("Wallet id cannot be null");
        }
        Validator.validateWallet(wallet);

        if(myInvestmentsRepository.isWalletEmpty(wallet, id)){
            wallet.setType(newType);
            walletDAO.update(wallet);
        } else{
            throw new RuntimeException("The wallet isn't empty");
        }
    }

    public void updateName(String id, Wallet wallet) {
        if (id == null) {
            throw new IllegalArgumentException("Wallet id cannot be null");
        }

        Validator.validateWallet(wallet);

        walletDAO.update(wallet);
    }
}
