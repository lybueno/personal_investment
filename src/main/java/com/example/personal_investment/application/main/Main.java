package com.example.personal_investment.application.main;

import com.example.personal_investment.application.data.dao.InMemoryStockDAO;
import com.example.personal_investment.application.data.dao.InMemoryUserDAO;
import com.example.personal_investment.application.data.dao.InMemoryWalletDAO;
import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.exceptions.EntityAlreadyExistsException;
import com.example.personal_investment.domain.exceptions.EntityNotExistsException;
import com.example.personal_investment.domain.exceptions.IncorrectPasswordException;
import com.example.personal_investment.domain.usecases.stock.*;
import com.example.personal_investment.domain.usecases.user.AuthenticateUserUC;
import com.example.personal_investment.domain.usecases.user.RegisterUserUC;
import com.example.personal_investment.domain.usecases.user.UserDAO;
import com.example.personal_investment.domain.usecases.wallet.AddWalletUC;
import com.example.personal_investment.domain.usecases.wallet.DeleteWalletUC;
import com.example.personal_investment.domain.usecases.wallet.UpdateWalletUC;
import com.example.personal_investment.domain.usecases.wallet.WalletDAO;

import java.math.BigDecimal;

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

    public static void main(String[] args) {
        injectDependencies();

        testUser();
        testStocks();

//        HelloApplication.main(args);
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
       Stock stock3 = new Stock("03",StockType.BDR,"ticker3","company3",
               "001/7777",valorStock);

        try{
                addStockUC.add(stock1);
                System.out.println("Stock "+stock1.getCompanyName()+" cadastrado com sucesso");

                addStockUC.add(stock2);
                System.out.println("Stock "+stock2.getCompanyName()+" cadastrado com sucesso");

//                updateStockUC.update(stock3);

                addStockUC.add(stock3);
                System.out.println("Stock "+stock3.getCompanyName()+" cadastrado com sucesso");

                deleteStockUC.delete(stock3);
                System.out.println("Stock "+stock3.getCompanyName()+" deletada com sucesso");

        } catch (EntityAlreadyExistsException e) {
                System.out.println(e.getMessage());
        }
   }


    private static void injectDependencies() {
        UserDAO userDAO = new InMemoryUserDAO();
        WalletDAO walletDAO = new InMemoryWalletDAO();
        StockDAO stockDAO = new InMemoryStockDAO();

        registerUserUC = new RegisterUserUC(userDAO);
        authenticateUserUC = new AuthenticateUserUC(userDAO);

        addStockUC = new AddStockUC(stockDAO);
        searchStockUC = new SearchStockUC(stockDAO);
        deleteStockUC = new DeleteStockUC(stockDAO);
        updateStockUC = new UpdateStockUC(stockDAO);

//        addWalletUC = new AddWalletUC(walletDAO);
//        updateWalletUC = new UpdateWalletUC(walletDAO);
//        deleteWalletUC = new DeleteWalletUC(walletDAO);
    }

}
