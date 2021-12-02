package com.example.personal_investment.application.controllers;

import com.example.personal_investment.application.common.Routes;
import com.example.personal_investment.application.view.Window;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class InvestmentsManagementController {
    @FXML
    private Button btnReturn;
    @FXML
    private Button btnLogout;

    public void backPreviousScreen(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.walletManagementPage);
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.loginPage);
    }
}
