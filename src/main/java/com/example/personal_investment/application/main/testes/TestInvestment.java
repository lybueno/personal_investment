package com.example.personal_investment.application.main.testes;

import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.entities.wallet.Wallet;

import java.math.BigDecimal;

import static com.example.personal_investment.application.main.Main.addInvestmentUC;
import static com.example.personal_investment.application.main.Main.registerUserUC;

public class TestInvestment {

    public static void testInvestment() {
        BigDecimal stockValue = new BigDecimal("0.5");
        BigDecimal totalAmount = new BigDecimal("12");

        Stock stock1 = new Stock("01", StockType.REGULAR,"ticker1","company1",
                "001/9999",stockValue);

        String username = "usuário 2";
        String password = "123456";
        String confirmPassword = "123456";
        User user = registerUserUC.signUp(username, password, confirmPassword);

        Wallet wallet = new Wallet("Test Wallet", StockType.REGULAR, user);

        Investment investment = new Investment(wallet,stock1,10,totalAmount);

        addInvestment(investment);

    }

    public static void addInvestment(Investment investment){
        try{
            addInvestmentUC.add(investment);
            System.out.println("Investimento cadastrado:"
                    + "\nID:" + investment.getId()
                    + "\nCarteira:" + investment.getWallet().getName()
                    + "\nTotal:" + investment.getTotalAmount()
                    + "\nQuantidade:" + investment.getQuantity()
                    + "\nStock:" +investment.getStock().getCompanyName());

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
