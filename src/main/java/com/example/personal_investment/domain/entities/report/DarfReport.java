package com.example.personal_investment.domain.entities.report;


import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.personal_investment.domain.entities.darf.Darf;
import com.example.personal_investment.domain.entities.user.User;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRCsvDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class DarfReport {
    public void imprimir(Darf darf, User user) {
        try {
            Map<String, Object> parametros = new HashMap();

            parametros.put("nameUser", user.getUsername());
            parametros.put("stockType", darf.getStockType().toString());
            parametros.put("dueDate", darf.getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            parametros.put("taxAmount", darf.getTaxAmount().toString());
            parametros.put("saleValue", darf.getSaleValue().toString());
            parametros.put("averagePurchaseValue", darf.getAveragePurchaseValue().toString());

            JasperReport report = JasperCompileManager
                    .compileReport("src/main/java/com/example/personal_investment/domain/jasper/" + "darf.jrxml");

            JasperPrint printer2 = JasperFillManager.fillReport(report,
                    parametros,new JREmptyDataSource());

            JasperViewer jv = new JasperViewer(printer2, false);
            jv.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
