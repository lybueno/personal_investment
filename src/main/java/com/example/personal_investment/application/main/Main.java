package com.example.personal_investment.application.main;

import com.example.personal_investment.application.data.dao.InMemoryUserDAO;
import com.example.personal_investment.application.data.dao.InMemoryWalletDAO;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.exceptions.EntityAlreadyExistsException;
import com.example.personal_investment.domain.exceptions.EntityNotExistsException;
import com.example.personal_investment.domain.exceptions.IncorrectPasswordException;
import com.example.personal_investment.domain.usecases.user.AuthenticateUserUC;
import com.example.personal_investment.domain.usecases.user.RegisterUserUC;
import com.example.personal_investment.domain.usecases.user.UserDAO;
import com.example.personal_investment.domain.usecases.wallet.AddWalletUC;
import com.example.personal_investment.domain.usecases.wallet.DeleteWalletUC;
import com.example.personal_investment.domain.usecases.wallet.UpdateWalletUC;
import com.example.personal_investment.domain.usecases.wallet.WalletDAO;

public class Main {
    public static RegisterUserUC registerUserUC;
    public static AuthenticateUserUC authenticateUserUC;
    public static AddWalletUC addWalletUC;
    public static UpdateWalletUC updateWalletUC;
    public static DeleteWalletUC deleteWalletUC;

    public static void main(String[] args) {
        injectDependencies();

        testUser();

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

    private static void injectDependencies() {
        UserDAO userDAO = new InMemoryUserDAO();
        WalletDAO walletDAO = new InMemoryWalletDAO();

        registerUserUC = new RegisterUserUC(userDAO);
        authenticateUserUC = new AuthenticateUserUC(userDAO);
//
//        addWalletUC = new AddWalletUC(walletDAO);
//        updateWalletUC = new UpdateWalletUC(walletDAO);
//        deleteWalletUC = new DeleteWalletUC(walletDAO);
    }

}
