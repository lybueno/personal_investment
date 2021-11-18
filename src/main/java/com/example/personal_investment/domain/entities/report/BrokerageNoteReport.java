package com.example.personal_investment.domain.entities.report;

import com.example.personal_investment.domain.entities.darf.Darf;
import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.entities.user.User;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class BrokerageNoteReport {
    public void imprimir(StockTransaction stockTransaction) {
        try {
            Map<String, Object> parametros = new HashMap();

            parametros.put("ticker", stockTransaction.getStock().getTicker());
            parametros.put("transactionAmount", stockTransaction.calculateTransactionAmount().toString());
            parametros.put("transactionDate", stockTransaction.getTransactionDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            parametros.put("quantity", stockTransaction.getQuantity().toString());
            parametros.put("unitaryValue", stockTransaction.getUnitaryValue().toString());
            parametros.put("transactionType", stockTransaction.getTransactionType().toString());
            parametros.put("cnpj", stockTransaction.getStock().getCnpj());
            parametros.put("companyName", stockTransaction.getStock().getCompanyName());
            parametros.put("nameUser", stockTransaction.getWallet().getUser().getUsername());

            JasperReport report = JasperCompileManager
                    .compileReport("src/main/java/com/example/personal_investment/domain/jasper/" + "notaNegociacao.jrxml");

            JasperPrint printer2 = JasperFillManager.fillReport(report,
                    parametros, new JREmptyDataSource());

            JasperViewer jv = new JasperViewer(printer2, false);
            jv.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
