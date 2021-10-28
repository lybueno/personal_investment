module com.example.personal_investment {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.example.personal_investment.application.view;
    opens com.example.personal_investment.application.view to javafx.fxml;
    exports com.example.personal_investment.application.controllers;
    opens com.example.personal_investment.application.controllers to javafx.fxml;
}