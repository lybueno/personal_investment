package com.example.personal_investment.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.personal_investment.application.main.Main.searchStockUC;

public class StockManagementController {
    @FXML
    public TextField tfSearchStock;
    @FXML
    public Label username;

    public void addStock(ActionEvent actionEvent) {

    }

    public void updateStock(ActionEvent actionEvent) {
    }

    public void deleteStock(ActionEvent actionEvent) {
    }

    public void searchStock(ActionEvent actionEvent) {
    }

    public void registerPurchaseStock(ActionEvent actionEvent) {
    }

    public void reportsPage(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.reportPage);
    }

    public void walletPage(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.walletPage);
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.loginPage);
    }

}
