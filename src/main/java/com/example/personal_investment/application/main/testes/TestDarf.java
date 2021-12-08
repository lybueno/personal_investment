package com.example.personal_investment.application.main.testes;

import com.example.personal_investment.application.data.sql.SqliteDarfDAO;
import com.example.personal_investment.application.data.sql.SqliteStockTransactionDAO;
import com.example.personal_investment.application.viewmodel.DarfVM;
import com.example.personal_investment.domain.entities.darf.Darf;
import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.entities.stock_transaction.TransactionType;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.exceptions.EntityAlreadyExistsException;
import com.example.personal_investment.domain.usecases.report.DarfReportUC;
import com.example.personal_investment.domain.usecases.stock_transaction.BrokerageNoteDAO;
import com.example.personal_investment.domain.usecases.stock_transaction.DarfDAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static com.example.personal_investment.application.main.Main.*;
import static com.example.personal_investment.application.main.testes.Reports.stockDAO;
import static com.example.personal_investment.application.main.testes.Reports.walletDAO;

public class TestDarf {
    static DarfDAO darfDAO = new SqliteDarfDAO();
    static BrokerageNoteDAO brokerageNoteDAO = new SqliteStockTransactionDAO();

    public static void testDarf(){
        boolean ifUserExist = findUserUC.findOneByUsername("mylla").isEmpty();
        Optional<User> user;
        if(ifUserExist){
            registerUserUC.signUp("mylla","123","123");
        }

        user = findUserUC.findOneByUsername("mylla");

        LocalDate dueDate = LocalDate.now().plusMonths(1);
        BigDecimal taxAmount = new BigDecimal("2.5");
        BigDecimal saleValue = new BigDecimal("15");
        BigDecimal averagePurchaseValue = new BigDecimal("20");

        Darf darf = new Darf(StockType.REGULAR, dueDate,taxAmount, saleValue, averagePurchaseValue);

        DarfVM darfVM = new DarfVM(darf);

        testAddDarf(darf);
        DarfReportUC darfReportUC = new DarfReportUC();

      //  darfReportUC.printDarf(darfVM, user.get());
    }

    public static void testIR(){

        boolean ifUserExist = findUserUC.findOneByUsername("mylla").isEmpty();
        Optional<User> user;

        if(ifUserExist){
            registerUserUC.signUp("mylla","123","123");
        }

        user = findUserUC.findOneByUsername("mylla");

        LocalDate transactionDate = LocalDate.now();
        BigDecimal stockValue = new BigDecimal("4.5");
        BigDecimal unitaryValue = new BigDecimal("2");

        Stock stock = new Stock(StockType.REGULAR,"PET45","Petrobras2",
                "33.000.167/0661-29",stockValue);
        stockDAO.insert(stock);

        Wallet wallet = new Wallet("Test Wallet 3", StockType.REGULAR, user.get());
        walletDAO.insert(wallet);

        StockTransaction stockTransaction = new StockTransaction(stock,wallet,transactionDate,14,
                unitaryValue, TransactionType.SALE);

        brokerageNoteDAO.insert(stockTransaction);
     //   List<IncomeTaxVM> incomeTax = new ArrayList<>();

        boolean ifNoteExist = searchBrokerageNoteUC.findAll().isEmpty();
        System.out.println(ifNoteExist);
    }

    public static void testAddDarf(Darf darf){
        try{
            darfDAO.insert(darf);
            System.out.println("------------------------------");
            System.out.println("darf cadastrado com sucesso");
            System.out.println("ID: "+darf.getId()+
                    " \nAverage Purchase Value: "+darf.getAveragePurchaseValue()+
                    " \nSaleValue: "+darf.getSaleValue()+
                    " \nStockType: "+darf.getStockType()+
                    " \nTaxAmount: "+darf.getTaxAmount()+
                    " \nDueDate: "+darf.getDueDate());
            System.out.println("------------------------------");
        } catch (EntityAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
    }
}
