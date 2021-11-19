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
    public void imprimir(StockTransaction stockTransaction, BigDecimal situationCurrentYear,  BigDecimal situationLastYear) {
        try {
            Map<String, Object> parametros = new HashMap();

            String currentYear =  "R$ "+situationCurrentYear.toString();
            String lastYear = "R$ "+situationLastYear.toString();
            String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String lastDate = LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String discrimination = "Empresa: "+stockTransaction.getStock().getCompanyName()+
                    ", Ticker: "+stockTransaction.getStock().getTicker()+
                    ", Quantidade: "+stockTransaction.getQuantity()+
                    ", Custo total: "+stockTransaction.calculateTransactionAmount(); //TODO verificar com o grupo sobre o custo total de aquisição
            String cnpj = stockTransaction.getStock().getCnpj();

            parametros.put("currentYear", currentYear);
            parametros.put("lastYear", lastYear);
            parametros.put("currentDate",currentDate);
            parametros.put("lastDate", lastDate);
            parametros.put("discrimination", discrimination);
            parametros.put("cnpj", cnpj);

            JasperReport report = JasperCompileManager
                    .compileReport("src/main/java/com/example/personal_investment/domain/jasper/impostoRenda.jrxml");

            JasperPrint printer2 = JasperFillManager.fillReport(report,
                    parametros,new JREmptyDataSource());

            JasperViewer jv = new JasperViewer(printer2, false);
            jv.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
