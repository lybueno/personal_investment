package com.example.personal_investment.application.main.testes;

import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.exceptions.EntityAlreadyExistsException;
import com.example.personal_investment.domain.exceptions.EntityNotExistsException;

import java.math.BigDecimal;

import static com.example.personal_investment.application.main.Main.*;

public class TestStock {
    public static void testStocks(){
        BigDecimal valorStock = new BigDecimal("0.5");

        Stock stock1 = new Stock("01", StockType.REGULAR,"ticker1","company1",
                "001/9999",valorStock);
        Stock stock2 = new Stock("02",StockType.BDR,"ticker2","company2",
                "001/8888",valorStock);
        Stock stock3 = new Stock("02",StockType.BDR,"ticker2","company2",
                "001/8888",valorStock);


        testAddStock(stock1);
        testAddStock(stock2);
        testAddStock(stock3);

        stock3 = new Stock("03",StockType.BDR,"ticker2","company2",
                "001/8888",valorStock);

        testAddStock(stock3);

        stock3 = new Stock("03",StockType.BDR,"ticker3","company2",
                "001/8888",valorStock);

        testAddStock(stock3);

        testFindAllStocks();
        testFindByTickerStocks("ticker3");
        testFindByTickerStocks("ticker323");
        testFindByTickerStocks("ticker2");
        testFindByTickerStocks("ticker1");
        testFindStockByCompanyName("company2");
        testFindStockByCompanyName("company223");
        testFindStockByCNPJ("001/9999");
        testFindStockByCNPJ("001/232323");

     //   testDeleteStock(stock1);
        testFindAllStocks();
        testAddStock(stock1);

        stock2 = new Stock("02",StockType.BDR,"ticker2","company22",
                "001/8888",valorStock);

        testUpdateStock(stock2);
    }

    public static void testAddStock(Stock stock){
        try{
            addStockUC.add(stock);
            System.out.println("------------------------------");
            System.out.println("Stock cadastrado com sucesso");
            System.out.println("ID: "+stock.getId()+" \nEmpresa: "+stock.getCompanyName()+" \nCNPJ: "+stock.getCnpj()+" \nTicker: "+stock.getTicker()+
                    " \nValor: "+stock.getStockQuote()+" \nTipo: "+stock.getType());
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
