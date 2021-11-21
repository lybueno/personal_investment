package com.example.personal_investment.application.main.testes;

import com.example.personal_investment.application.data.dao.InMemoryDarfDAO;
import com.example.personal_investment.domain.entities.darf.Darf;
import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.entities.stock_transaction.TransactionType;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.exceptions.EntityNotExistsException;
import com.example.personal_investment.domain.usecases.stock_transaction.DarfDAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.example.personal_investment.application.main.Main.*;

public class TestTransactionPurchaseAndSale {
    public static void testTransactionPurchase(){
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
            System.out.println("Compra no valor de " + transactionPurchase.calculateTransactionAmount() + " efetuada com sucesso");
        } catch (EntityNotExistsException e){
            System.out.println(e.getMessage());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static void testTransactionSale(){
        DarfDAO darfDAO = new InMemoryDarfDAO();
        User user = new User("Hedy Lamarr", "ladyTech");
        Stock stock = new Stock(StockType.BDR,"1",  "PEPB34", "PEPSICO Inc", new BigDecimal("61.08"));
        Wallet wallet = new Wallet("MyBDRs", StockType.BDR, user);
        LocalDate date = LocalDate.of(2021, 9, 10);
        StockTransaction transactionPurchase = new StockTransaction("1", stock, wallet, date,200, new BigDecimal
                ("52.20"), TransactionType.PURCHASE);
        registerStockPurchaseUC.purchase(transactionPurchase);
//        StockTransaction transactionSale = new StockTransaction("1", stock,null, LocalDate.now(), 100, new BigDecimal(
//                "61.08"), TransactionType.SALE);
        StockTransaction transactionSale = new StockTransaction("1", stock,wallet, LocalDate.now(), 100, new BigDecimal(
                "61.08"), TransactionType.SALE);
        BigDecimal valuteTax =  calculateTaxAmountUC.calculate(transactionSale);
        try {
            registerStockSaleUC.sell(transactionSale, valuteTax);
            System.out.println("Venda no valor de " + transactionSale.calculateTransactionAmount() + " efetuada com " +
                    "sucesso");
            System.out.println("Imposto a pagar: " + valuteTax.setScale(2));
            List<Darf> darfs = darfDAO.findAll();
            for (Darf darf: darfs) {
                System.out.println(darf);
            }
        } catch (EntityNotExistsException e){
            System.out.println(e.getMessage());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
