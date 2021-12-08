package com.example.personal_investment.application.main;

import com.example.personal_investment.application.data.sql.*;
import com.example.personal_investment.application.main.testes.Reports;
import com.example.personal_investment.application.main.testes.TestDarf;
import com.example.personal_investment.application.view.Window;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.usecases.stock.*;
import com.example.personal_investment.domain.usecases.stock_transaction.*;
import com.example.personal_investment.domain.usecases.user.AuthenticateUserUC;
import com.example.personal_investment.domain.usecases.user.FindUserUC;
import com.example.personal_investment.domain.usecases.user.RegisterUserUC;
import com.example.personal_investment.domain.usecases.user.UserDAO;
import com.example.personal_investment.domain.usecases.wallet.*;

import java.util.List;

public class Main {
    public static RegisterUserUC registerUserUC;
    public static AuthenticateUserUC authenticateUserUC;
    public static FindUserUC findUserUC;

    public static AddWalletUC addWalletUC;
    public static UpdateWalletUC updateWalletUC;
    public static DeleteWalletUC deleteWalletUC;
    public static SearchWalletUC searchWalletUC;

    public static SearchInvestmentUC searchInvestmentUC;

    public static AddStockUC addStockUC;
    public static DeleteStockUC deleteStockUC;
    public static SearchStockUC searchStockUC;
    public static UpdateStockUC updateStockUC;

    public static RegisterStockSaleUC registerStockSaleUC;
    public static RegisterStockPurchaseUC registerStockPurchaseUC;
    public static CalculateTaxAmountUC calculateTaxAmountUC;

    public static SearchBrokerageNoteUC searchBrokerageNoteUC;
    public static CalculateStockIncomeUC calculateStockIncomeUC;

    public static SearchDarfUC searchDarfUC;

    public static void main(String[] args) throws InterruptedException {
        injectDependencies();
        setupDatabase();

//        TestUser.testUser();
//        TestStock.testStocks();
//        Reports.printDarf();
//        Reports.printIR();
//          Reports.printTradingNote();
//        TestCalculateTax.testCalculateTax();
//        TestTransactionPurchaseAndSale.testTransactionPurchase();
//          TestBrokerageNote.testBrokerageNote();
//        TestTransactionPurchaseAndSale.testTransactionSale();
//        TestInvestment.testInvestment();
//        TestWallet.testWallet();
          TestDarf.testDarf();
//          TestDarf.testIR();

//        ImportUpdatedPriceFromAPI test = new ImportUpdatedPriceFromAPI("PETR4");
//        BigDecimal updatedPrice = test.getData();
//        System.out.println(updatedPrice);

        Window.main(args);


    }

    private static void setupDatabase() {
        DatabaseBuilder dbBuilder = new DatabaseBuilder();
        dbBuilder.buildDatabaseIfMissing();
    }

    private static void injectDependencies() {
        //UserDAO userDAO = new InMemoryUserDAO();
        UserDAO userDAO = new SqliteUserDAO();
        //WalletDAO walletDAO = new InMemoryWalletDAO();
        WalletDAO walletDAO = new SqliteWalletDAO();
        //StockDAO stockDAO = new InMemoryStockDAO();
        StockDAO stockDAO = new SqliteStockDAO();
        //BrokerageNoteDAO brokerageNoteDAO = new InMemoryStockTransactionDAO();
        BrokerageNoteDAO brokerageNoteDAO = new SqliteStockTransactionDAO();
        //InvestmentsDAO investmentsDAO = new InMemoryInvestmentDAO();
        InvestmentsDAO investmentsDAO = new SqliteInvestmentDAO();
        //DarfDAO darfDAO = new InMemoryDarfDAO();
        DarfDAO darfDAO = new SqliteDarfDAO();

        registerUserUC = new RegisterUserUC(userDAO);
        authenticateUserUC = new AuthenticateUserUC(userDAO);
        findUserUC = new FindUserUC(userDAO);

        addStockUC = new AddStockUC(stockDAO);
        searchStockUC = new SearchStockUC(stockDAO);
        deleteStockUC = new DeleteStockUC(stockDAO,investmentsDAO);
        updateStockUC = new UpdateStockUC(stockDAO);

        addWalletUC = new AddWalletUC(walletDAO);
        updateWalletUC = new UpdateWalletUC(walletDAO);
        searchWalletUC = new SearchWalletUC(walletDAO);
        deleteWalletUC = new DeleteWalletUC(walletDAO);

        searchInvestmentUC = new SearchInvestmentUC(investmentsDAO);

        registerStockPurchaseUC = new RegisterStockPurchaseUC(brokerageNoteDAO, investmentsDAO);
        registerStockSaleUC = new RegisterStockSaleUC(investmentsDAO, brokerageNoteDAO, darfDAO);
        calculateTaxAmountUC = new CalculateTaxAmountUC(brokerageNoteDAO, investmentsDAO);

        searchBrokerageNoteUC = new SearchBrokerageNoteUC(brokerageNoteDAO);
        calculateStockIncomeUC = new CalculateStockIncomeUC(walletDAO, brokerageNoteDAO);

        searchDarfUC = new SearchDarfUC(darfDAO);
    }


}