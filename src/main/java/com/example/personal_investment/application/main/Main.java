package com.example.personal_investment.application.main;

import com.example.personal_investment.application.data.sql.*;
import com.example.personal_investment.application.view.Window;
import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.usecases.stock.*;
import com.example.personal_investment.domain.usecases.stock_transaction.*;
import com.example.personal_investment.domain.usecases.user.AuthenticateUserUC;
import com.example.personal_investment.domain.usecases.user.FindUserUC;
import com.example.personal_investment.domain.usecases.user.RegisterUserUC;
import com.example.personal_investment.domain.usecases.user.UserDAO;
import com.example.personal_investment.domain.usecases.wallet.*;

import java.math.BigDecimal;


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

//        User user = registerUserUC.signUp("System","123", "123");
//        createMockStocks();
//        createMockWallets(user);

        Window.main(args);
    }


    private static void createMockWallets(User user) {
        Wallet walletBdr = new Wallet("Carteira BDR", StockType.BDR, user);
        Wallet walletRegular = new Wallet("Carteira REGULAR", StockType.REGULAR, user);
        Wallet walletFII = new Wallet("Carteira FII", StockType.FII, user);

        addWalletUC.insert(walletBdr);
        addWalletUC.insert(walletRegular);
        addWalletUC.insert(walletFII);
    }

    private static void createMockStocks() {
        Stock petr4 = new Stock(StockType.REGULAR, "PETR4", "PETROBRAS PN", "33.000.167/0001-01", new BigDecimal("29.35"));
        Stock wege3 = new Stock(StockType.REGULAR, "WEGE3", "WEG S.A.", "84.429.695/0001-11", new BigDecimal("36.05"));
        Stock itub4 = new Stock(StockType.REGULAR, "ITUB4", "ITAUUNIBANCO PN", "60.872.504/0001-23", new BigDecimal("22.74"));

        Stock aapl34 = new Stock(StockType.BDR, "AAPL34", "APPLE", "00.623.904/0001-73", new BigDecimal("96.75"));
        Stock nflx34 = new Stock(StockType.BDR, "NFLX34", "NETFLIX", "13.590.585/0001-99", new BigDecimal("69.46"));

        Stock grld11 = new Stock(StockType.FII, "GRLD11 ", "GRLOUVEIRA", "22.480.639/0001-54", new BigDecimal("145.50"));
        Stock cpds11 = new Stock(StockType.FII, "CPDS11", "CAPITÂNIA SECURITES", "31.154.416/0001-98", new BigDecimal("97.92"));

        Stock bova11 = new Stock(StockType.ETF_GENERAL, "BOVA11", "ISHARES BOVA", "10.406.511/0001-61", new BigDecimal("104.20"));

        addStockUC.add(petr4);
        addStockUC.add(wege3);
        addStockUC.add(itub4);
        addStockUC.add(aapl34);
        addStockUC.add(nflx34);
        addStockUC.add(grld11);
        addStockUC.add(cpds11);
        addStockUC.add(bova11);

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
        deleteStockUC = new DeleteStockUC(stockDAO, investmentsDAO);
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