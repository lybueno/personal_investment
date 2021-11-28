package com.example.personal_investment.domain.usecases.report;

import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class BrokerageNoteReportUC {
    public void printTradingNote(StockTransaction stockTransaction) {
        try {
            Map<String, Object> parameters = new HashMap();

            String ticker = stockTransaction.getStock().getTicker();
            String transactionAmount = "R$ " + stockTransaction.calculateTransactionAmount().toString();
            String transactionDate = stockTransaction.getTransactionDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String quantity = stockTransaction.getQuantity().toString();
            String unitaryValue = "R$ " + stockTransaction.getUnitaryValue().toString();
            String transactionType = stockTransaction.getTransactionType().toString();
            String cnpj = stockTransaction.getStock().getCnpj();
            String companyName = stockTransaction.getStock().getCompanyName();
            String nameUser = stockTransaction.getWallet().getUser().getUsername();

            parameters.put("ticker", ticker);
            parameters.put("transactionAmount", transactionAmount);
            parameters.put("transactionDate", transactionDate);
            parameters.put("quantity", quantity);
            parameters.put("unitaryValue", unitaryValue);
            parameters.put("transactionType", transactionType);
            parameters.put("cnpj", cnpj);
            parameters.put("companyName", companyName);
            parameters.put("nameUser", nameUser);

            JasperReport report = JasperCompileManager.compileReport("src/main/java/com/example/personal_investment/domain/jasper/notaNegociacao.jrxml");

            JasperPrint printer = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());

            JasperViewer jv = new JasperViewer(printer, false);
            jv.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
