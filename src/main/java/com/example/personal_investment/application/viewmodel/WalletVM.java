package com.example.personal_investment.application.viewmodel;

import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.entities.wallet.Wallet;

public final class WalletVM {
    private final String id;
    private final String name;
    private final String stockType;
    private final String totalValue;

    public WalletVM(String id, String name, String stockType, String totalValue) {
        this.id = id;
        this.name = name;
        this.stockType = stockType;
        this.totalValue = totalValue;
    }

    public WalletVM(Wallet wallet) {
        this.id = wallet.getId();
        this.name = wallet.getName();
        this.stockType = wallet.getType().toString();
        this.totalValue = wallet.getTotalInvestmentsValue().toString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStockType() {
        return stockType;
    }

    public String getTotalValue() {
        return totalValue;
    }

    public Wallet setWalletEntity(User user){
        return new Wallet(this.id, this.name, StockType.toEnum(stockType), user);
    }
}
