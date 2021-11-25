package com.example.personal_investment.application.main;

import com.example.personal_investment.application.data.inmemory.*;
import com.example.personal_investment.application.main.testes.*;
import com.example.personal_investment.domain.usecases.stock.*;
import com.example.personal_investment.domain.usecases.stock_transaction.*;
import com.example.personal_investment.domain.usecases.user.AuthenticateUserUC;
import com.example.personal_investment.domain.usecases.user.RegisterUserUC;
import com.example.personal_investment.domain.usecases.user.UserDAO;
import com.example.personal_investment.domain.usecases.wallet.*;

public class Main {
    public static RegisterUserUC registerUserUC;
    public static AuthenticateUserUC authenticateUserUC;

    public static AddWalletUC addWalletUC;
    public static UpdateWalletUC updateWalletUC;
    public static DeleteWalletUC deleteWalletUC;

    public static AddStockUC addStockUC;
    public static DeleteStockUC deleteStockUC;
    public static SearchStockUC searchStockUC;
    public static UpdateStockUC updateStockUC;

    public static RegisterStockSaleUC registerStockSaleUC;
    public static RegisterStockPurchaseUC registerStockPurchaseUC;
    public static CalculateTaxAmountUC calculateTaxAmountUC;

    public static AddBrokerageNoteUC addBrokerageNoteUC;
    public static AddInvestmentUC addInvestmentUC;

    public static CalculateStockIncomeUC calculateStockIncomeUC;

    public static void main(String[] args) {
        injectDependencies();

        TestUser.testUser();
        TestStock.testStocks();
        Reports.printDarf();
        Reports.printIR();
        Reports.printTradingNote();
        TestCalculateTax.testCalculateTax();
        TestTransactionPurchaseAndSale.testTransactionPurchase();
        TestBrokerageNote.testBrokerageNote();
        TestTransactionPurchaseAndSale.testTransactionSale();
        TestInvestment.testInvestment();
        TestWallet.testWallet();

//        ImportUpdatedPriceFromAPI test = new ImportUpdatedPriceFromAPI("PETR4");
//        BigDecimal updatedPrice = test.getData();
//        System.out.println(updatedPrice);

//        HelloApplication.main(args);

    }



    private static void injectDependencies() {
        UserDAO userDAO = new InMemoryUserDAO();
        WalletDAO walletDAO = new InMemoryWalletDAO();
        StockDAO stockDAO = new InMemoryStockDAO();
        BrokerageNoteDAO brokerageNoteDAO = new InMemoryStockTransactionDAO();
        InvestmentsDAO investmentsDAO = new InMemoryInvestmentDAO();
        DarfDAO darfDAO = new InMemoryDarfDAO();

        registerUserUC = new RegisterUserUC(userDAO);
        authenticateUserUC = new AuthenticateUserUC(userDAO);

        addStockUC = new AddStockUC(stockDAO);
        searchStockUC = new SearchStockUC(stockDAO);
        deleteStockUC = new DeleteStockUC(stockDAO);
        updateStockUC = new UpdateStockUC(stockDAO);

        addWalletUC = new AddWalletUC(walletDAO);
        updateWalletUC = new UpdateWalletUC(walletDAO);
        deleteWalletUC = new DeleteWalletUC(walletDAO);

        registerStockPurchaseUC = new RegisterStockPurchaseUC(brokerageNoteDAO, investmentsDAO);
        registerStockSaleUC = new RegisterStockSaleUC(investmentsDAO, brokerageNoteDAO, darfDAO);
        calculateTaxAmountUC = new CalculateTaxAmountUC(brokerageNoteDAO, investmentsDAO);

        addBrokerageNoteUC = new AddBrokerageNoteUC(brokerageNoteDAO);

        addInvestmentUC = new AddInvestmentUC(investmentsDAO);

        calculateStockIncomeUC =  new CalculateStockIncomeUC(walletDAO, brokerageNoteDAO);
    }


}