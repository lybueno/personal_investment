package com.example.personal_investment.application.controllers;

import com.example.personal_investment.application.common.Routes;
import com.example.personal_investment.application.common.UIMode;
import com.example.personal_investment.application.common.UserSingleton;
import com.example.personal_investment.application.view.Window;
import com.example.personal_investment.application.viewmodel.StockVM;
import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock_transaction.TransactionType;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.exceptions.WalletIsNotEmptyException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.personal_investment.application.main.Main.deleteStockUC;
import static com.example.personal_investment.application.main.Main.searchStockUC;

public class StockManagementController {
    ObservableList<StockVM> snapshot;
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
    private User user;

    @FXML
    private void initialize() throws IOException {
        user = UserSingleton.getInstance().getUser();
        if (user == null) {
            Window.setRoot(Routes.loginPage);
        }

        username.setText(user.getUsername());

        bindTableViewToItemsList();
        bindColumnsToValueSources();
        loadList();

        searchStockListener();
    }

    private void searchStockListener() {
        FilteredList<StockVM> filteredStocks = new FilteredList<>(snapshot, b -> true);
        tfSearchStock.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredStocks.setPredicate(stockVM -> {
                if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }

                String searchStock = newValue.toLowerCase();

                if(stockVM.getTicker().toLowerCase().contains(searchStock)){
                    return true;
                } else if(stockVM.getCompanyName().contains(searchStock)) {
                    return true;
                } else return stockVM.getCnpj().contains(searchStock);
            });
        });
        tbStocks.setItems(filteredStocks);
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
        setStockInController(null, UIMode.INSERT);
    }

    public void updateStock(ActionEvent actionEvent) throws IOException {
        StockVM selectedStock = tbStocks.getSelectionModel().getSelectedItem();
        if (selectedStock != null) {
            Window.setRoot(Routes.stockPage);
            setStockInController(selectedStock.toStockEntity(), UIMode.UPDATE);
        }
    }

    private void setStockInController(Stock stock, UIMode uiMode) throws IOException {
        StockController controller = (StockController) Window.getController();
        controller.setData(stock, uiMode);
    }

    public void deleteStock(ActionEvent actionEvent) {
        StockVM selectedStock = tbStocks.getSelectionModel().getSelectedItem();
        if (selectedStock != null) {
            try {

                deleteStockUC.deleteById(selectedStock.getId());
            } catch (WalletIsNotEmptyException __) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Gerenciamento de Ações");
                alert.setHeaderText("Erro, não foi possivel deletar a ação");
                alert.setContentText("Possuem investimentos cadastrados com essa ação, caso deseje realmente exclui-la, venda todos os investimentos antes.");

                alert.showAndWait();
            }
        }
        loadList();
    }

    public void registerPurchaseStock(ActionEvent actionEvent) throws IOException {
        StockVM selectedStock = tbStocks.getSelectionModel().getSelectedItem();
        if (selectedStock != null) {
            Window.setRoot(Routes.stockTransactionPage);
            setDataInInvestmentPage(selectedStock.toStockEntity());
        }
    }

    public void reportsPage(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.reportManagementPage);
    }

    public void walletPage(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.walletManagementPage);
//        WalletManagementController controller = (WalletManagementController) Window.getController();
//        controller.setData(user);
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.loginPage);
    }

    public void setDataInInvestmentPage(Stock stock) throws IOException {
        StockTransactionController controller = (StockTransactionController) Window.getController();
        controller.setData(null, stock, TransactionType.PURCHASE);
    }

}
