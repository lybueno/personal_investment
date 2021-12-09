package com.example.personal_investment.domain.usecases.report;

import com.example.personal_investment.application.viewmodel.BrokerageNoteVM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.util.HashMap;
import java.util.Map;

public class BrokerageNoteReportUC {
    public void printTradingNote(BrokerageNoteVM brokerageNoteVM) {
        try {
            Map<String, Object> parameters = new HashMap();

            String ticker = brokerageNoteVM.getTicker();
            String transactionAmount = "R$ " + brokerageNoteVM.getTransactionAmount();
            String transactionDate = brokerageNoteVM.getTransactionDate();
            String quantity = brokerageNoteVM.getQuantity();
            String unitaryValue = "R$ " + brokerageNoteVM.getUnitaryValue();
            String transactionType = brokerageNoteVM.getTransactionType();
            String cnpj = brokerageNoteVM.getCnpj();
            String companyName = brokerageNoteVM.getCompanyName();
            String nameUser = brokerageNoteVM.getNameUser();

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
