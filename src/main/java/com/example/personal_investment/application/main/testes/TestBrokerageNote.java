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

public class TestBrokerageNote {
    public static void testBrokerageNote(){
        Optional<User> user = findUserUseCase.findOneByUsername("mylla");

        LocalDate transactionDate = LocalDate.now();
        BigDecimal valorStock = new BigDecimal("4.5");
        BigDecimal unitaryValue = new BigDecimal("2");

        Stock stock = new Stock("01", StockType.REGULAR,"PETR4","Petrobras",
                "33.000.167/0661-29",valorStock);

        Wallet wallet = new Wallet("Test Wallet", StockType.REGULAR, user.get());

        StockTransaction stockTransaction = new StockTransaction("01",stock,wallet,transactionDate,5,
                unitaryValue, TransactionType.SALE);

        addBrokerageNote(stockTransaction);

    }

    public static void addBrokerageNote(StockTransaction stockTransaction) {
        try{
            addBrokerageNoteUC.add(stockTransaction);
            System.out.println("Nota criada, abrindo relatório..");
          //  Reports.printTradingNoteWhenAddBrokerageNote(stockTransaction);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
