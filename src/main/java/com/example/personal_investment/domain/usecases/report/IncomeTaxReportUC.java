package com.example.personal_investment.domain.usecases.report;

import com.example.personal_investment.application.viewmodel.IncomeTaxVM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.util.HashMap;
import java.util.Map;

public class IncomeTaxReportUC {
    public void printIR(IncomeTaxVM incomeTaxVM) {
        try {
            Map<String, Object> parameters = new HashMap();

            String currentYear =  "R$ "+incomeTaxVM.getCurrentYear();
            String lastYear = "R$ "+incomeTaxVM.getLastYear();
            String currentDate = incomeTaxVM.getCurrentDate();
            String lastDate = incomeTaxVM.getLastDate();
            String discrimination = incomeTaxVM.getDiscrimination();
            String cnpj = incomeTaxVM.getCnpj();

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
