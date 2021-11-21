package com.example.personal_investment.domain.entities.report;

import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class IncomeTaxReport {
    public void printIR(StockTransaction stockTransaction, BigDecimal situationCurrentYear,  BigDecimal situationLastYear) {
        try {
            Map<String, Object> parameters = new HashMap();

            String currentYear =  "R$ "+situationCurrentYear.toString();
            String lastYear = "R$ "+situationLastYear.toString();
            String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String lastDate = LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String discrimination = "Empresa: "+stockTransaction.getStock().getCompanyName()+
                    ", Ticker: "+stockTransaction.getStock().getTicker()+
                    "\nQuantidade: "+stockTransaction.getQuantity()+
                    ", Custo total: "+stockTransaction.calculateTransactionAmount();
            String cnpj = stockTransaction.getStock().getCnpj();

            parameters.put("currentYear", currentYear);
            parameters.put("lastYear", lastYear);
            parameters.put("currentDate",currentDate);
            parameters.put("lastDate", lastDate);
            parameters.put("discrimination", discrimination);
            parameters.put("cnpj", cnpj);

            JasperReport report = JasperCompileManager.compileReport("src/main/java/com/example/personal_investment/domain/jasper/impostoRenda.jrxml");

            JasperPrint printer = JasperFillManager.fillReport(report, parameters ,new JREmptyDataSource());

            JasperViewer jv = new JasperViewer(printer, false);
            jv.setVisible(true);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
