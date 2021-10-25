module com.example.personal_investment {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.personal_investment to javafx.fxml;
    exports com.example.personal_investment;
}