package com.example.personal_investment.application.main.testes;

import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.exceptions.EntityNotExistsException;
import com.example.personal_investment.domain.exceptions.WalletIsNotEmptyException;

import java.math.BigDecimal;
import java.util.Optional;

import static com.example.personal_investment.application.main.Main.*;

public class TestWallet {
    public static void testWallet(){
        String username = "TESTEUSER";
        String password = "123456";
        String confirmPassword = "123456";
        Optional<User> user = findUserUseCase.findOneByUsername("mylla");

        Wallet wallet = new Wallet("Test Wallet", StockType.REGULAR, user.get());

        Wallet walletNullUser= new Wallet("Wallet Null User", StockType.REGULAR, null);

        Wallet walletNotRegister = new Wallet("Not Register", StockType.REGULAR, user.get());

        testAddWallet(wallet);
        System.out.println("------------------------------");

        System.out.println("Adicionando carteira com user null");
        testAddWallet(walletNullUser);
        System.out.println("------------------------------");

        System.out.println("Atualizando nome e tipo de carteira(carteira esta vazia)");
        wallet.setName("New Test Name");
        try {
            wallet.setType(StockType.BDR);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

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
        Wallet walletSameId = new Wallet(wallet.getId(), "Test Wallet", StockType.REGULAR, user.get());

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
}
