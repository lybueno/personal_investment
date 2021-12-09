module com.example.personal_investment {
    requires javafx.controls;
    requires javafx.fxml;
    requires jasperreports;
    requires java.sql;
    requires sqlite.jdbc;

    opens com.example.personal_investment.domain.entities.darf to javafx.base;
    opens com.example.personal_investment.domain.entities.investment to javafx.base;
    opens com.example.personal_investment.domain.entities.stock to javafx.base;
    opens com.example.personal_investment.domain.entities.stock_transaction to javafx.base;
    opens com.example.personal_investment.domain.entities.tax to javafx.base;
    opens com.example.personal_investment.domain.entities.user to javafx.base;
    opens com.example.personal_investment.domain.entities.wallet to javafx.base;
    opens com.example.personal_investment.application.viewmodel to javafx.base;

    exports com.example.personal_investment.application.view;
    opens com.example.personal_investment.application.view to javafx.fxml;
    exports com.example.personal_investment.application.controllers;
    opens com.example.personal_investment.application.controllers to javafx.fxml;
}