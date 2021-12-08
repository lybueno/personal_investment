package com.example.personal_investment.application.main.testes;

import com.example.personal_investment.application.data.sql.SqliteStockDAO;
import com.example.personal_investment.application.data.sql.SqliteStockTransactionDAO;
import com.example.personal_investment.application.data.sql.SqliteWalletDAO;
import com.example.personal_investment.domain.entities.darf.Darf;
import com.example.personal_investment.domain.usecases.report.BrokerageNoteReportUC;
import com.example.personal_investment.domain.usecases.report.DarfReportUC;
import com.example.personal_investment.domain.usecases.report.IncomeTaxReportUC;
import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.entities.stock_transaction.TransactionType;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.usecases.stock.StockDAO;
import com.example.personal_investment.domain.usecases.stock_transaction.BrokerageNoteDAO;
import com.example.personal_investment.domain.usecases.wallet.WalletDAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static com.example.personal_investment.application.main.Main.findUserUC;

public class Reports {
    static BrokerageNoteDAO brokerageNoteDAO = new SqliteStockTransactionDAO();
    static WalletDAO walletDAO = new SqliteWalletDAO();
    static StockDAO stockDAO = new SqliteStockDAO();
    public static void printIR() {
        IncomeTaxReportUC incomeTaxReport = new IncomeTaxReportUC();

        BigDecimal situationCurrentYear = new BigDecimal("12.9");
        BigDecimal situationLastYear = new BigDecimal("15.3");

        LocalDate transactionDate = LocalDate.now();
        BigDecimal stockValue = new BigDecimal("4.5");
        BigDecimal unitaryValue = new BigDecimal("2");

        Optional<User> user = findUserUC.findOneByUsername("mylla");

        Stock stock = new Stock(StockType.REGULAR,"PETR6","Petrobras",
                "33.000.167/0661-29",stockValue);
     //   stockDAO.insert(stock);

        Wallet wallet = new Wallet("Test Wallet", StockType.REGULAR, user.get());
      //  walletDAO.insert(wallet);

        StockTransaction stockTransaction = new StockTransaction(stock,wallet,transactionDate,5,
                unitaryValue, TransactionType.SALE);

      //  brokerageNoteDAO.insert(stockTransaction);



//        incomeTaxReport.printIR(stockTransaction,situationCurrentYear,situationLastYear);
    }

    public static void printDarf() {
        DarfReportUC darfReport = new DarfReportUC();

        Optional<User> user = findUserUC.findOneByUsername("mylla");

        LocalDate dueDate = LocalDate.now().plusMonths(1);
        BigDecimal taxAmount = new BigDecimal("2.5");
        BigDecimal saleValue = new BigDecimal("15");
        BigDecimal averagePurchaseValue = new BigDecimal("20");

        Darf darf = new Darf(StockType.REGULAR, dueDate,taxAmount, saleValue, averagePurchaseValue);

//        darfReport.printDarf(darf,user);
    }

    public static void printTradingNote() {
        BrokerageNoteReportUC brokerageNoteReport = new BrokerageNoteReportUC();

        Optional<User> user = findUserUC.findOneByUsername("mylla");

        LocalDate transactionDate = LocalDate.now();
        BigDecimal valorStock = new BigDecimal("4.5");
        BigDecimal unitaryValue = new BigDecimal("2");

        Stock stock = new Stock(StockType.REGULAR,"PE98","Pet",
                "33.000.167/0661-29",valorStock);
        stockDAO.insert(stock);

        Wallet wallet = new Wallet("Wallet", StockType.REGULAR, user.get());
        walletDAO.insert(wallet);

        StockTransaction stockTransaction = new StockTransaction(stock,wallet,transactionDate,5,
                unitaryValue, TransactionType.SALE);
        brokerageNoteDAO.insert(stockTransaction);

//        brokerageNoteReport.printTradingNote(stockTransaction);
    }

    public static void printTradingNoteWhenAddBrokerageNote(StockTransaction stockTransaction) {
        BrokerageNoteReportUC brokerageNoteReport = new BrokerageNoteReportUC();

     //   brokerageNoteReport.printTradingNote(stockTransaction);
    }
}
