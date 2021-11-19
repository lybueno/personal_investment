package com.example.personal_investment.domain.entities.report;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.example.personal_investment.domain.entities.darf.Darf;
import com.example.personal_investment.domain.entities.user.User;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

public class DarfReport {
    public void imprimir(Darf darf, User user) {
        try {
            Map<String, Object> parametros = new HashMap();

            String nameUser = user.getUsername();
            String stockType = darf.getStockType().toString();
            String dueDate = darf.getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String taxAmount = darf.getTaxAmount().toString();
            String saleValue = darf.getSaleValue().toString();
            String averagePurchaseValue = darf.getAveragePurchaseValue().toString();

            parametros.put("nameUser", nameUser);
            parametros.put("stockType", stockType);
            parametros.put("dueDate", dueDate);
            parametros.put("taxAmount", taxAmount);
            parametros.put("saleValue", saleValue);
            parametros.put("averagePurchaseValue", averagePurchaseValue);

            JasperReport report = JasperCompileManager
                    .compileReport("src/main/java/com/example/personal_investment/domain/jasper/darf.jrxml");

            JasperPrint printer2 = JasperFillManager.fillReport(report,
                    parametros,new JREmptyDataSource());

            JasperViewer jv = new JasperViewer(printer2, false);
            jv.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
