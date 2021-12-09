package com.example.personal_investment.application.viewmodel;

import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class IncomeTaxVM {
    private final String cnpj;
    private final String discrimination;
    private final String lastYear;
    private final String currentYear;
    private final String lastDate;
    private final String currentDate;

    public IncomeTaxVM(String cnpj,
                       String discrimination,
                       String lastYear,
                       String currentYear,
                       String lastDate, String currentDate) {
        this.cnpj = cnpj;
        this.discrimination = discrimination;
        this.lastYear = lastYear;
        this.currentYear = currentYear;
        this.lastDate = lastDate;
        this.currentDate = currentDate;
    }

    public IncomeTaxVM(StockTransaction stockTransaction, BigDecimal situationCurrentYear, BigDecimal situationLastYear) {
        this.cnpj = stockTransaction.getStock().getCnpj();
        this.discrimination = "Empresa: " + stockTransaction.getStock().getCompanyName() +
                ", Ticker: " + stockTransaction.getStock().getTicker() +
                "\nQuantidade: " + stockTransaction.getQuantity() +
                ", Custo total: " + stockTransaction.calculateTransactionAmount();
        this.lastYear = situationLastYear.toString();
        this.currentYear = situationCurrentYear.toString();
        this.currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.lastDate = LocalDate.now().minusYears(1).withDayOfMonth(LocalDate.now().lengthOfMonth())
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

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

    public String getLastDate() {
        return lastDate;
    }

    public String getCurrentDate() {
        return currentDate;
    }
}
