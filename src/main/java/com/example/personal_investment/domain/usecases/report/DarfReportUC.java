package com.example.personal_investment.domain.usecases.report;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.example.personal_investment.application.viewmodel.DarfVM;
import com.example.personal_investment.domain.entities.user.User;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

public class DarfReportUC {
    public void printDarf(DarfVM darf, User user) {
        try {
            Map<String, Object> parameters = new HashMap();

            String nameUser = user.getUsername();
            String stockType = darf.getStockType();
            String dueDate = darf.getDueDate();
            String taxAmount = darf.getTaxAmount();
            String saleValue = "R$ " + darf.getSaleValue();
            String averagePurchaseValue = "R$ " + darf.getAveragePurchaseValue();

            parameters.put("nameUser", nameUser);
            parameters.put("stockType", stockType);
            parameters.put("dueDate", dueDate);
            parameters.put("taxAmount", taxAmount);
            parameters.put("saleValue", saleValue);
            parameters.put("averagePurchaseValue", averagePurchaseValue);

            JasperReport report = JasperCompileManager.compileReport("src/main/java/com/example/personal_investment/domain/jasper/darf.jrxml");

            JasperPrint printer = JasperFillManager.fillReport(report, parameters,new JREmptyDataSource());

            JasperViewer jv = new JasperViewer(printer, false);
            jv.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
