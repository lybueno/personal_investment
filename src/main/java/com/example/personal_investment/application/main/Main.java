package com.example.personal_investment.application.main;

import com.example.personal_investment.application.data.dao.*;
import com.example.personal_investment.domain.entities.darf.Darf;
import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.report.BrokerageNoteReport;
import com.example.personal_investment.domain.entities.report.DarfReport;
import com.example.personal_investment.domain.entities.report.IncomeTaxReport;
import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.entities.stock_transaction.TransactionType;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.exceptions.EntityAlreadyExistsException;
import com.example.personal_investment.domain.exceptions.EntityNotExistsException;
import com.example.personal_investment.domain.exceptions.IncorrectPasswordException;
import com.example.personal_investment.domain.exceptions.WalletIsNotEmptyException;
import com.example.personal_investment.domain.usecases.stock.*;
import com.example.personal_investment.domain.usecases.stock_transaction.*;
import com.example.personal_investment.domain.usecases.user.AuthenticateUserUC;
import com.example.personal_investment.domain.usecases.user.RegisterUserUC;
import com.example.personal_investment.domain.usecases.user.UserDAO;
import com.example.personal_investment.domain.usecases.wallet.*;

import java.math.BigDecimal;
import java.time.LocalDate;

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


    public static void main(String[] args) {
        injectDependencies();


        //testUser();
        //testStocks();
        //printDarf();
        //printTradingNote();
        //printIR();
        //testWallet();

        //testCalculateTax();
        testTransactionPurchase();

//        ImportUpdatedPriceFromAPI test = new ImportUpdatedPriceFromAPI("PETR4");
//        BigDecimal updatedPrice = test.getData();
//        System.out.println(updatedPrice);

//        HelloApplication.main(args);

    }

    private static void printIR() {
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

    private static void printDarf() {
        DarfReport darfReport = new DarfReport();

        User user = registerUserUC.signUp("Mylla", "12345", "12345");

        LocalDate dueDate = LocalDate.now().plusMonths(1);
        BigDecimal taxAmount = new BigDecimal("2.5");
        BigDecimal saleValue = new BigDecimal("15");
        BigDecimal averagePurchaseValue = new BigDecimal("20");

        Darf darf = new Darf(StockType.REGULAR, dueDate,taxAmount, saleValue, averagePurchaseValue);

        darfReport.printDarf(darf,user);
    }

    private static void printTradingNote() {
        BrokerageNoteReport brokerageNoteReport = new BrokerageNoteReport();

        User user = registerUserUC.signUp("Usuário teste", "12345", "12345");

        LocalDate transactionDate = LocalDate.now();
        BigDecimal valorStock = new BigDecimal("4.5");
        BigDecimal unitaryValue = new BigDecimal("2");

        Stock stock = new Stock("01",StockType.REGULAR,"PETR4","Petrobras",
                "33.000.167/0661-29",valorStock);

        Wallet wallet = new Wallet("Test Wallet", StockType.REGULAR, user);

        StockTransaction stockTransaction = new StockTransaction("01",stock,wallet,transactionDate,5,
                unitaryValue, TransactionType.SALE);

        brokerageNoteReport.printTradingNote(stockTransaction);
    }

    private static void testUser() {
        String username = "danielribas";
        String password = "123456";
        String confirmPassword = "123456";

        testUserSignup(username, password, " ");
        testUserSignup(username, password, password);
        testUserSignup(username, "", "");

        System.out.println("----");

        testUserLogin("dsadas", password);
        testUserLogin(username, "123");
        testUserLogin(username, password);
    }

    private static void testUserLogin(String username, String password) {
        try {
            User user = authenticateUserUC.login(username, password);
            System.out.println("Usuario "+user.getUsername()+" logado com sucesso");
        } catch (EntityNotExistsException e) {
            System.out.println("Usuario não existe");
        } catch (IncorrectPasswordException e) {
            System.out.println("Senha incorreta");
        } catch (Exception e) {
            System.out.println("Desculpe, ocorreu algum erro ao se logar");
        }
    }

    private static void testUserSignup(String username, String password, String confirmPassword) {
        try {
            User user = registerUserUC.signUp(username, password, confirmPassword);
            System.out.println("Usuario "+user.getUsername()+" cadastrado com sucesso");
        } catch (EntityAlreadyExistsException e) {
            System.out.println("Usuario ja existe");
        } catch (IncorrectPasswordException e) {
            System.out.println("Senha e confirmar senha não são iguais");
        } catch (Exception e) {
            System.out.println("Desculpe, ocorreu algum erro ao se cadastrar");
        }
    }

   private static void testStocks(){
       BigDecimal valorStock = new BigDecimal("0.5");

       Stock stock1 = new Stock("01",StockType.REGULAR,"ticker1","company1",
               "001/9999",valorStock);
       Stock stock2 = new Stock("02",StockType.BDR,"ticker2","company2",
               "001/8888",valorStock);
       Stock stock3 = new Stock("02",StockType.BDR,"ticker2","company2",
               "001/8888",valorStock);


       testAddStock(stock1);
       testAddStock(stock2);
       testAddStock(stock3);

       stock3 = new Stock("03",StockType.BDR,"ticker2","company2",
               "001/8888",valorStock);

       testAddStock(stock3);

       stock3 = new Stock("03",StockType.BDR,"ticker3","company2",
               "001/8888",valorStock);

       testAddStock(stock3);

       testFindAllStocks();
       testFindByTickerStocks("ticker3");
       testFindByTickerStocks("ticker323");
       testFindByTickerStocks("ticker2");
       testFindByTickerStocks("ticker1");
       testFindStockByCompanyName("company2");
       testFindStockByCompanyName("company223");
       testFindStockByCNPJ("001/9999");
       testFindStockByCNPJ("001/232323");

       testDeleteStock(stock1);
       testFindAllStocks();
       testAddStock(stock1);

       stock2 = new Stock("02",StockType.BDR,"ticker2","company22",
               "001/8888",valorStock);

       testUpdateStock(stock2);
   }

   private static void testAddStock(Stock stock){
       try{
           addStockUC.add(stock);
           System.out.println("------------------------------");
           System.out.println("Stock cadastrado com sucesso");
           System.out.println("ID: "+stock.getId()+" \nEmpresa: "+stock.getCompanyName()+" \nCNPJ: "+stock.getCnpj()+" \nTicker: "+stock.getTicker()+
                   " \nValor: "+stock.getStockQuote()+" \nTipo: "+stock.getType());
           System.out.println("------------------------------");
       } catch (EntityAlreadyExistsException e) {
           System.out.println(e.getMessage());
       }
   }

   private static void testUpdateStock(Stock stock){
        try{
            updateStockUC.update(stock);
            System.out.println("------------------------------");
            System.out.println("Stock atualizada com sucesso");
            System.out.println("ID: "+stock.getId()+" \nEmpresa: "+stock.getCompanyName()+" \nCNPJ: "+stock.getCnpj()+" \nTicker: "+stock.getTicker()+
                    " \nValor: "+stock.getStockQuote()+" \nTipo: "+stock.getType());
            System.out.println("------------------------------");
        } catch (EntityNotExistsException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testDeleteStock(Stock stock){
        try{
            deleteStockUC.delete(stock);
            System.out.println("------------------------------");
            System.out.println("Stock deletada com sucesso");
        } catch (EntityNotExistsException e) {
            System.out.println(e.getMessage());
        }
    }

   private static void testFindAllStocks(){
       try{
           System.out.println("-------- Ações cadastradas --------");
           searchStockUC.findAll().forEach(stock ->
                   {
                       System.out.println("ID: "+stock.getId()+" \nEmpresa: "+stock.getCompanyName()+" \nCNPJ: "+stock.getCnpj()+" \nTicker: "+stock.getTicker()+
                               " \nValor: "+stock.getStockQuote()+" \nTipo: "+stock.getType());
                       System.out.println("------------------------------");
                   }
           );
       }catch (EntityNotExistsException e){
           System.out.println(e.getMessage());
       }
   }

    private static void testFindStockByCompanyName(String companyName){
        try{
            boolean listStock = searchStockUC.findByCompanyName(companyName).isEmpty();
            if(!listStock){
                System.out.println("-------- Ações cadastradas da empresa: "+companyName);
                searchStockUC.findByCompanyName(companyName).forEach(
                        stock ->
                        {

                            System.out.println("ID: "+stock.getId()+" \nEmpresa: "+stock.getCompanyName()+" \nCNPJ: "+stock.getCnpj()+" \nTicker: "+stock.getTicker()+
                                    " \nValor: "+stock.getStockQuote()+" \nTipo: "+stock.getType());
                            System.out.println("------------------------------");
                        }
                );
            }else {
                System.out.println("Não existe ação cadastrada com esse nome de empresa: "+companyName);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void testFindStockByCNPJ(String cnpj){
        try{
            boolean listStock = searchStockUC.findByCNPJ(cnpj).isEmpty();
            if(!listStock){
                System.out.println("-------- Ações cadastradas da empresa: "+cnpj);
                searchStockUC.findByCNPJ(cnpj).forEach(
                        stock ->
                        {
                            System.out.println("ID: "+stock.getId()+" \nEmpresa: "+stock.getCompanyName()+" \nCNPJ: "+stock.getCnpj()+" \nTicker: "+stock.getTicker()+
                                    " \nValor: "+stock.getStockQuote()+" \nTipo: "+stock.getType());
                            System.out.println("------------------------------");
                        }
                );
            }else {
                System.out.println("Não existe ação cadastrada com esse CNPJ: "+cnpj);
            }


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void testFindByTickerStocks(String ticker){
        try{
            searchStockUC.findByTicker(ticker).ifPresentOrElse(
                    (stock) ->
                    {
                        System.out.println("------------------------------");
                        System.out.println("ID: " + stock.getId() + " \nEmpresa: " + stock.getCompanyName() + " \nCNPJ: " + stock.getCnpj() + " \nTicker: " + stock.getTicker() +
                                " \nValor: " + stock.getStockQuote() + " \nTipo: " + stock.getType());
                        System.out.println("------------------------------");
                    },
                    () -> System.out.println("Não existe ação cadastrada com esse ticker: "+ticker)
            );
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void testWallet(){
        String username = "RENAN";
        String password = "123456";
        String confirmPassword = "123456";
        User user = registerUserUC.signUp(username, password, confirmPassword);

        Wallet wallet = new Wallet("Test Wallet", StockType.REGULAR, user);

        Wallet walletNullUser= new Wallet("Wallet Null User", StockType.REGULAR, null);

        Wallet walletNotRegister = new Wallet("Not Register", StockType.REGULAR, user);

        testAddWallet(wallet);
        System.out.println("------------------------------");

        System.out.println("Adicionando carteira com user null");
        testAddWallet(walletNullUser);
        System.out.println("------------------------------");

        System.out.println("Atualizando nome e tipo de carteira(carteira esta vazia)");
        wallet.setName("New Test Name");
        wallet.setType(StockType.BDR);

        testUpdateWallet(wallet);

        System.out.println("------------------------------");

        System.out.println("Atualizando carteira com investimento");
        Stock stock = new Stock("01",StockType.REGULAR,"ticker1","company1",
                "001/9999", new BigDecimal("0.5"));
        Investment investment = new Investment("4", wallet, stock, 2, new BigDecimal("10.0"));
        wallet.addInvestment(investment);

        testUpdateWallet(wallet);

        System.out.println("------------------------------");

        System.out.println("Tentando mudar tipo carteira(carteira nao esta vazia)");
        //não e possivel atualizar passando como parametro wallet que foi utilizado wallet.setType
        //por causa do model, e walletFromDAO
        Wallet walletSameId = new Wallet(wallet.getId(), "Test Wallet", StockType.REGULAR, user);

        testUpdateWallet(walletSameId);

        System.out.println("------------------------------");

        System.out.println("Atualizar nome carteira(carteira nao esta vazia)");
        wallet.setName("New Name Test Wallet");

        testUpdateWallet(wallet);

        System.out.println("------------------------------");

        System.out.println("Atualizando carteira não registrada");
        testDeleteWallet(walletNotRegister);

        System.out.println("------------------------------");

        System.out.println("Removendo carteira não vazia");
        testDeleteWallet(wallet);

        System.out.println("------------------------------");

        System.out.println("Removendo carteira vazia");
        wallet.removeInvestmentByTicker("ticker1");

        testDeleteWallet(wallet);

        System.out.println("------------------------------");

        System.out.println("Excluindo Carteira não cadastrada no DAO");
        testDeleteWallet(walletNotRegister);
    }

    private static void testAddWallet(Wallet wallet){
        try{
            addWalletUC.insert(wallet);
            System.out.println("Carteira: " + wallet.getName() + " | Tipo: " + wallet.getType() + "\nCarteira criada com sucesso");
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testUpdateWallet(Wallet wallet){
        try{
            updateWalletUC.update(wallet);
            System.out.println("Carteira: " + wallet.getName() + " | Tipo: " + wallet.getType() + "\nCarteira atualizada com sucesso");
        } catch(EntityNotExistsException e){
            System.out.println(e.getMessage());
        } catch (WalletIsNotEmptyException e){
            System.out.println(e.getMessage());
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void testDeleteWallet(Wallet wallet){
        try{
            deleteWalletUC.delete(wallet);
            System.out.println("Carteira removida com sucesso");
        } catch (WalletIsNotEmptyException e){
            System.out.println(e.getMessage());
        } catch (EntityNotExistsException e){
            System.out.println(e.getMessage());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void testCalculateTax(){
        Stock stockFII = new Stock(
                StockType.FII,
                "tickerFII",
                "company",
                "001/9999",
                new BigDecimal("100.0")
        );

        Stock stockBDR = new Stock(
                StockType.BDR,
                "tickerBDR",
                "company",
                "001/9999",
                new BigDecimal("100.0")
        );

        Stock stockETFGENERAL = new Stock(
                StockType.ETF_GENERAL,
                "tickerETFGENERAL",
                "company",
                "001/9999",
                new BigDecimal("100.0")
        );

        Stock stockETFREALESTATE = new Stock(
                StockType.ETF_REAL_ESTATE,
                "tickerETFREALSTATE",
                "company",
                "001/9999",
                new BigDecimal("100.0")
        );

        Stock stockREGULAR = new Stock(
                StockType.REGULAR,
                "tickerREGULAR",
                "company",
                "001/9999",
                new BigDecimal("100.0")
        );

        String username = "RENAN";
        String password = "123456";
        String confirmPassword = "123456";
        User user = registerUserUC.signUp(username, password, confirmPassword);

        Wallet walletFI = new Wallet("Wallet FI", StockType.FII, user);
        Wallet walletBDR = new Wallet("Wallet BDR", StockType.BDR, user);
        Wallet walletETFGENERAL = new Wallet("Wallet ETF GENERAL", StockType.ETF_GENERAL, user);
        Wallet walletETFREALESTATE = new Wallet("Wallet ETF REAL ESTATE", StockType.ETF_REAL_ESTATE, user);
        Wallet walletREGULAR = new Wallet("Wallet ETF REAL ESTATE", StockType.REGULAR, user);

        printTaxValue(stockFII, walletFI, 2, 1, "200.00");

        System.out.println("----------------------");

        printTaxValue(stockBDR, walletBDR, 2, 2, "200.00");

        System.out.println("----------------------");

        printTaxValue(stockETFGENERAL, walletETFGENERAL, 2, 1, "200.00");

        System.out.println("----------------------");

        printTaxValue(stockETFREALESTATE, walletETFREALESTATE, 2, 2,"200.00");

        System.out.println("----------------------");

        printTaxValue(stockREGULAR, walletREGULAR, 2, 1,"200.00");


    }

    private static void printTaxValue(Stock stock, Wallet wallet, Integer quantityPurchase, Integer quantitySale, String newStockValue){
        try {
            StockTransaction transactionPurchase = new StockTransaction(
                    "1",
                    stock,
                    wallet,
                    LocalDate.now(),
                    quantityPurchase,
                    stock.getStockQuote(),
                    TransactionType.PURCHASE
            );

            StockTransaction transactionSale = new StockTransaction(
                    "2",
                    stock,
                    wallet,
                    LocalDate.now(),
                    quantitySale,
                    new BigDecimal(newStockValue),
                    TransactionType.SALE
            );

            registerStockPurchaseUC.purchase(transactionPurchase);

            BigDecimal valuteTax =  calculateTaxAmountUC.calculate(transactionSale);
            System.out.println("Tipo ação: " + stock.getType()+
                    " | Valor ação na compra: R$ " + stock.getStockQuote()
                    + " | Valor ação na venda: R$ " + newStockValue
                    + "\nQuantidade vendida: " + quantitySale
                    + " | Total Imposto: R$: " + valuteTax.setScale(2));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void testTransactionPurchase(){
        User user = new User("Hedy Lamarr", "ladyTech");
        Stock stock = new Stock(StockType.BDR,"1",  "PEPB34", "PEPSICO Inc", new BigDecimal("61.08"));
        Wallet wallet = new Wallet("MyBDRs", StockType.BDR, user);
        LocalDate date = LocalDate.of(2021, 9, 10);
//        StockTransaction transactionPurchase = new StockTransaction("1", null, wallet, date,200, new BigDecimal("52" +
//                ".20"), TransactionType.PURCHASE);
//        StockTransaction transactionPurchase = new StockTransaction("1", stock, null, date,200, new BigDecimal("52" +
//                ".20"), TransactionType.PURCHASE);
//        StockTransaction transactionPurchase = new StockTransaction("1", stock, wallet, null,200, new BigDecimal("52" +
//                ".20"), TransactionType.PURCHASE);
//        StockTransaction transactionPurchase = new StockTransaction("1", stock, wallet, date,null, new BigDecimal("52" +
//                ".20"), TransactionType.PURCHASE);
//        StockTransaction transactionPurchase = new StockTransaction("1", stock, wallet, date,200, null,
//                TransactionType.PURCHASE);
//        StockTransaction transactionPurchase = new StockTransaction("1", stock, wallet, date,200, new BigDecimal
//         ("52.20"), null);
        StockTransaction transactionPurchase = new StockTransaction("1", stock, wallet, date,200, new BigDecimal
                ("52.20"), TransactionType.PURCHASE);
        try {
            registerStockPurchaseUC.purchase(transactionPurchase);
            System.out.println("Compra efetuada com sucesso");
        } catch (EntityNotExistsException e){
            System.out.println(e.getMessage());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

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
    }


}
