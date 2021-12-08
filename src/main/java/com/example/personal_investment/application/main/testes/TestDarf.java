package com.example.personal_investment.application.main.testes;

import com.example.personal_investment.application.common.UserSingleton;
import com.example.personal_investment.application.data.inmemory.InMemoryDarfDAO;
import com.example.personal_investment.application.data.sql.SqliteDarfDAO;
import com.example.personal_investment.application.viewmodel.DarfVM;
import com.example.personal_investment.domain.entities.darf.Darf;
import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.exceptions.EntityAlreadyExistsException;
import com.example.personal_investment.domain.usecases.report.DarfReportUC;
import com.example.personal_investment.domain.usecases.stock_transaction.DarfDAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static com.example.personal_investment.application.main.Main.*;

public class TestDarf {
    static DarfDAO darfDAO = new SqliteDarfDAO();


    public static void testDarf(){
        Optional<User> user = findUserUC.findOneByUsername("mylla");

        LocalDate dueDate = LocalDate.now().plusMonths(1);
        BigDecimal taxAmount = new BigDecimal("2.5");
        BigDecimal saleValue = new BigDecimal("15");
        BigDecimal averagePurchaseValue = new BigDecimal("20");

        Darf darf = new Darf(StockType.REGULAR, dueDate,taxAmount, saleValue, averagePurchaseValue);

        DarfVM darfVM = new DarfVM(darf);

        testAddDarf(darf);
        DarfReportUC darfReportUC = new DarfReportUC();

        darfReportUC.printDarf(darfVM, user.get());
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
