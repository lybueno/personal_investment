package com.example.personal_investment.domain.entities.report;

import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class BrokerageNoteReport {
    public void imprimir(StockTransaction stockTransaction) {
        try {
            Map<String, Object> parametros = new HashMap();

            String ticker = stockTransaction.getStock().getTicker();
            String transactionAmount = stockTransaction.calculateTransactionAmount().toString();
            String transactionDate = stockTransaction.getTransactionDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String quantity = stockTransaction.getQuantity().toString();
            String unitaryValue = stockTransaction.getUnitaryValue().toString();
            String transactionType = stockTransaction.getTransactionType().toString();
            String cnpj = stockTransaction.getStock().getCnpj();
            String companyName = stockTransaction.getStock().getCompanyName();
            String nameUser = stockTransaction.getWallet().getUser().getUsername();

            parametros.put("ticker", ticker);
            parametros.put("transactionAmount", transactionAmount);
            parametros.put("transactionDate", transactionDate);
            parametros.put("quantity", quantity);
            parametros.put("unitaryValue", unitaryValue);
            parametros.put("transactionType", transactionType);
            parametros.put("cnpj", cnpj);
            parametros.put("companyName", companyName);
            parametros.put("nameUser", nameUser);

            JasperReport report = JasperCompileManager
                    .compileReport("src/main/java/com/example/personal_investment/domain/jasper/notaNegociacao.jrxml");

            JasperPrint printer2 = JasperFillManager.fillReport(report,
                    parametros, new JREmptyDataSource());

            JasperViewer jv = new JasperViewer(printer2, false);
            jv.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
