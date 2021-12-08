package com.example.personal_investment.domain.usecases.wallet;

import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.exceptions.EntityNotExistsException;
import com.example.personal_investment.domain.exceptions.WalletIsNotEmptyException;
import com.example.personal_investment.domain.utils.Validator;

public class DeleteWalletUC {
    private final WalletDAO walletDAO;

    public DeleteWalletUC(WalletDAO walletDAO) {
        this.walletDAO = walletDAO;
    }

    public void delete(Wallet wallet) {
        if (wallet.getId() == null) {
            throw new IllegalArgumentException("Wallet id cannot be null");
        }
        Validator.validateWallet(wallet);

        Wallet walletFromDAO = walletDAO.findOneByWalletWithInvestments(wallet.getId()).orElseThrow(() -> new EntityNotExistsException("Wallet " +
                "not exists"));

        if (!walletFromDAO.isEmpty()) {
            throw new WalletIsNotEmptyException("Cannot delete wallet, is not empty");
        }

        walletDAO.delete(wallet);
    }
}
