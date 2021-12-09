package com.example.personal_investment.application.main.testes;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.entities.stock_transaction.TransactionType;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.entities.wallet.Wallet;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static com.example.personal_investment.application.main.Main.*;

public class TestCalculateTax {
    public static void testCalculateTax(){
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
        Optional<User> user = findUserUC.findOneByUsername("mylla");

        Wallet walletFI = new Wallet("Wallet FI", StockType.FII, user.get());
        Wallet walletBDR = new Wallet("Wallet BDR", StockType.BDR, user.get());
        Wallet walletETFGENERAL = new Wallet("Wallet ETF GENERAL", StockType.ETF_GENERAL, user.get());
        Wallet walletETFREALESTATE = new Wallet("Wallet ETF REAL ESTATE", StockType.ETF_REAL_ESTATE, user.get());
        Wallet walletREGULAR = new Wallet("Wallet ETF REAL ESTATE", StockType.REGULAR, user.get());

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
    public static void printTaxValue(Stock stock, Wallet wallet, Integer quantityPurchase, Integer quantitySale, String newStockValue){
        try {
            StockTransaction transactionPurchase = new StockTransaction(
                    stock,
                    wallet,
                    LocalDate.now(),
                    quantityPurchase,
                    stock.getStockQuote(),
                    TransactionType.PURCHASE
            );

            StockTransaction transactionSale = new StockTransaction(
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
}
