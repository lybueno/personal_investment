package com.example.personal_investment.application.controllers;

import com.example.personal_investment.application.common.Routes;
import com.example.personal_investment.application.view.Window;
import com.example.personal_investment.application.viewmodel.StockVM;
import com.example.personal_investment.domain.entities.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import static com.example.personal_investment.application.main.Main.deleteStockUC;
import static com.example.personal_investment.application.main.Main.searchStockUC;

public class StockManagementController {
    @FXML
    private Label username;

    @FXML
    private TableView<StockVM> tbStocks;

    @FXML
    private TextField tfSearchStock;

    @FXML
    private TableColumn<StockVM, String> cTicker;

    @FXML
    private TableColumn<StockVM, String> cCompanyName;

    @FXML
    private TableColumn<StockVM, String> cCNPJ;

    @FXML
    private TableColumn<StockVM, String> cStockType;

    @FXML
    private TableColumn<StockVM, String> cStockValue;

    ObservableList<StockVM> snapshot;

    private User user;

    public void setData(User user) throws IOException {
        if(user == null){
            Window.setRoot(Routes.loginPage);
        }
        this.user = user;
    }

    @FXML
    private void initialize() {
        bindTableViewToItemsList();
        bindColumnsToValueSources();
        loadList();
    }

    private void bindColumnsToValueSources() {
        cTicker.setCellValueFactory(new PropertyValueFactory<>("ticker"));
        cCNPJ.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        cCompanyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        cStockType.setCellValueFactory(new PropertyValueFactory<>("type"));
        cStockValue.setCellValueFactory(new PropertyValueFactory<>("stockQuote"));
    }

    private void bindTableViewToItemsList() {
        snapshot = FXCollections.observableArrayList();
        tbStocks.setItems(snapshot);
    }

    private void loadList() {
        List<StockVM> stocks = searchStockUC.findAll().stream().map(
                StockVM::new
        ).collect(Collectors.toList());
        snapshot.clear();
        snapshot.addAll(stocks);
    }

    public void addStock(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.stockPage);
    }

    public void updateStock(ActionEvent actionEvent) {
    }

    public void deleteStock(ActionEvent actionEvent) {
        StockVM selectedStock = tbStocks.getSelectionModel().getSelectedItem();
        if (selectedStock != null) {
            String id = selectedStock.getId();
            deleteStockUC.deleteById(id);
        }
        loadList();
    }

    public void searchStock(ActionEvent actionEvent) {
    }

    public void registerPurchaseStock(ActionEvent actionEvent) {
    }

    public void reportsPage(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.reportManagementPage);
    }

    public void walletPage(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.walletManagementPage);
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.loginPage);
    }

}
