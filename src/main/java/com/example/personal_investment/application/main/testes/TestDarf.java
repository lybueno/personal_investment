package com.example.personal_investment.application.main.testes;

import com.example.personal_investment.application.data.inmemory.InMemoryDarfDAO;
import com.example.personal_investment.domain.entities.darf.Darf;
import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.exceptions.EntityAlreadyExistsException;
import com.example.personal_investment.domain.exceptions.EntityNotExistsException;
import com.example.personal_investment.domain.usecases.stock_transaction.DarfDAO;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.example.personal_investment.application.main.Main.*;
import static com.example.personal_investment.application.main.Main.searchStockUC;

public class TestDarf {
    static DarfDAO darfDAO = new InMemoryDarfDAO();

    public static void testDarf(){
        User user = registerUserUC.signUp("Mylla", "12345", "12345");

        LocalDate dueDate = LocalDate.now().plusMonths(1);
        BigDecimal taxAmount = new BigDecimal("2.5");
        BigDecimal saleValue = new BigDecimal("15");
        BigDecimal averagePurchaseValue = new BigDecimal("20");

        Darf darf = new Darf(StockType.REGULAR, dueDate,taxAmount, saleValue, averagePurchaseValue);

        testAddDarf(darf);
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

    public static void testUpdateStock(Stock stock){
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

    public static void testDeleteStock(Stock stock){
        try{
            deleteStockUC.delete(stock);
            System.out.println("------------------------------");
            System.out.println("Stock deletada com sucesso");
        } catch (EntityNotExistsException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testFindAllStocks(){
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

    public static void testFindStockByCompanyName(String companyName){
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

    public static void testFindStockByCNPJ(String cnpj){
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

    public static void testFindByTickerStocks(String ticker){
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
}
