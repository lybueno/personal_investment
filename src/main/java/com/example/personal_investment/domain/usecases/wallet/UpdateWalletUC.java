package com.example.personal_investment.domain.usecases.wallet;

import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.exceptions.EntityNotExistsException;
import com.example.personal_investment.domain.exceptions.WalletIsNotEmptyException;
import com.example.personal_investment.domain.utils.Validator;

public class UpdateWalletUC {
    private final WalletDAO walletDAO;

    public UpdateWalletUC(WalletDAO walletDAO) {
        this.walletDAO = walletDAO;
    }

    public void update(Wallet wallet) {
        if (wallet.getId() == null) {
            throw new IllegalArgumentException("Wallet id cannot be null");
        }
        Validator.validateWallet(wallet);

        Wallet walletFromDAO = walletDAO.findOneByWalletWithInvestments(wallet.getId()).orElseThrow(() -> new EntityNotExistsException("Wallet not" +
                " exists"));

        if (walletFromDAO.getType() != wallet.getType() && !walletFromDAO.isEmpty()) {
            throw new WalletIsNotEmptyException("Tried to change type but wallet is not empty");
        }

        walletDAO.update(wallet);
    }
}
