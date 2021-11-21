package com.example.personal_investment.application.main.testes;

import com.example.personal_investment.domain.entities.darf.Darf;
import com.example.personal_investment.domain.entities.report.BrokerageNoteReport;
import com.example.personal_investment.domain.entities.report.DarfReport;
import com.example.personal_investment.domain.entities.report.IncomeTaxReport;
import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.entities.stock_transaction.TransactionType;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.entities.wallet.Wallet;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.example.personal_investment.application.main.Main.registerUserUC;

public class Reports {
    public static void printIR() {
        IncomeTaxReport incomeTaxReport = new IncomeTaxReport();

        BigDecimal situationCurrentYear = new BigDecimal("12.9");
        BigDecimal situationLastYear = new BigDecimal("15.3");

        LocalDate transactionDate = LocalDate.now();
        BigDecimal stockValue = new BigDecimal("4.5");
        BigDecimal unitaryValue = new BigDecimal("2");

        User user = registerUserUC.signUp("Myllena", "12345", "12345");

        Stock stock = new Stock(StockType.REGULAR,"PETR4","Petrobras",
                "33.000.167/0661-29",stockValue);

        Wallet wallet = new Wallet("Test Wallet", StockType.REGULAR, user);

        StockTransaction stockTransaction = new StockTransaction("01",stock,wallet,transactionDate,5,
                unitaryValue, TransactionType.SALE);

        incomeTaxReport.printIR(stockTransaction,situationCurrentYear,situationLastYear);
    }

    public static void printDarf() {
        DarfReport darfReport = new DarfReport();

        User user = registerUserUC.signUp("Mylla", "12345", "12345");

        LocalDate dueDate = LocalDate.now().plusMonths(1);
        BigDecimal taxAmount = new BigDecimal("2.5");
        BigDecimal saleValue = new BigDecimal("15");
        BigDecimal averagePurchaseValue = new BigDecimal("20");

        Darf darf = new Darf(StockType.REGULAR, dueDate,taxAmount, saleValue, averagePurchaseValue);

        darfReport.printDarf(darf,user);
    }

     public static void printTradingNote(StockTransaction stockTransaction) {
        BrokerageNoteReport brokerageNoteReport = new BrokerageNoteReport();

//        User user = registerUserUC.signUp("Usuário teste", "12345", "12345");
//
//        LocalDate transactionDate = LocalDate.now();
//        BigDecimal valorStock = new BigDecimal("4.5");
//        BigDecimal unitaryValue = new BigDecimal("2");
//
//        Stock stock = new Stock("01",StockType.REGULAR,"PETR4","Petrobras",
//                "33.000.167/0661-29",valorStock);
//
//        Wallet wallet = new Wallet("Test Wallet", StockType.REGULAR, user);
//
//        StockTransaction stockTransaction = new StockTransaction("01",stock,wallet,transactionDate,5,
//                unitaryValue, TransactionType.SALE);

        brokerageNoteReport.printTradingNote(stockTransaction);
    }
}
