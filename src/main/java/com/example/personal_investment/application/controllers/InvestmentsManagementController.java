package com.example.personal_investment.application.controllers;

import com.example.personal_investment.application.common.Routes;
import com.example.personal_investment.application.view.Window;
import com.example.personal_investment.application.viewmodel.InvestmentVM;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class InvestmentsManagementController {

    @FXML
    private Label username;

    @FXML
    private Button btnReturn;
    @FXML
    private Button btnLogout;

    @FXML
    private TableView<InvestmentVM> tbInvestments;

    @FXML
    private TableColumn<InvestmentVM, String> cTicker;

    @FXML
    private TableColumn<InvestmentVM, String> cAverageValue;

    @FXML
    private TableColumn<InvestmentVM, String> cStockType;

    @FXML
    private TableColumn cNote;

    @FXML
    private TableColumn<InvestmentVM, String> cAmount;

    @FXML
    private TableColumn cPurchase;

    @FXML
    private TableColumn cSale;

    public void backPreviousScreen(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.walletManagementPage);
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.loginPage);
    }
}
