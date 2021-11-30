package com.example.personal_investment.application.viewmodel;

import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;

import java.math.BigDecimal;

public final class IncomeTaxVM {
    private final String cnpj;
    private final String discrimination;
    private final String lastYear;
    private final String currentYear;
    private final String currentDate;
    private final String lastDate;

    public IncomeTaxVM(String cnpj,
                       String discrimination,
                       String lastYear,
                       String currentYear,
                       String currentDate,
                       String lastDate) {
        this.cnpj = cnpj;
        this.discrimination = discrimination;
        this.lastYear = lastYear;
        this.currentYear = currentYear;
        this.currentDate = currentDate;
        this.lastDate = lastDate;
    }

    //revert, aparenta estar confuso

//    public IncomeTaxVM(StockTransaction stockTransaction,
//                       BigDecimal situationCurrentYear,
//                       BigDecimal situationLastYear) {
//        this.cnpj = stockTransaction.getStock().getCnpj();
//        this.discrimination = "Empresa: "+stockTransaction.getStock().getCompanyName()+
//                ", Ticker: "+stockTransaction.getStock().getTicker()+
//                "\nQuantidade: "+stockTransaction.getQuantity()+
//                ", Custo total: "+stockTransaction.calculateTransactionAmount();
//        this.lastYear = lastYear;
//        this.currentYear = currentYear;
//        this.currentDate = currentDate;
//        this.lastDate = lastDate;
//    }

    public String getCnpj() {
        return cnpj;
    }

    public String getDiscrimination() {
        return discrimination;
    }

    public String getLastYear() {
        return lastYear;
    }

    public String getCurrentYear() {
        return currentYear;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public String getLastDate() {
        return lastDate;
    }
}
