package com.example.personal_investment.application.controllers;

import com.example.personal_investment.application.common.Routes;
import com.example.personal_investment.application.view.Window;
import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.exceptions.EntityAlreadyExistsException;
import com.example.personal_investment.domain.exceptions.IncorrectPasswordException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.math.BigDecimal;

import static com.example.personal_investment.application.main.Main.addStockUC;


public class StockAddController {
    @FXML
    private TextField txtCompanyName;
    @FXML
    private TextField txtCnpj;
    @FXML
    private TextField txtStockQuote;
    @FXML
    private TextField txtTicker;
    @FXML
    private ComboBox cBoxStockType;
    @FXML
    private Label systemMessage;

    ObservableList<StockType> stockTypes;

    @FXML
    private void initialize() {
       bindComboBoxToStockType();
       loadListStockType();
    }

    private void bindComboBoxToStockType(){
        stockTypes = FXCollections.observableArrayList();
        cBoxStockType.setItems(stockTypes);
    }

    private void loadListStockType() {
        stockTypes.clear();
        stockTypes.addAll(StockType.values());
    }

    public void registerStock(ActionEvent actionEvent) {
        if (isFilledInputs()) {
            try {
                BigDecimal stockQuote = new BigDecimal(txtStockQuote.getText());
                    Stock stock = new Stock(StockType.toEnum(cBoxStockType.getValue().toString()),txtTicker.getText(),txtCompanyName.getText(),txtCnpj.getText(), stockQuote);
                    addStockUC.add(stock);
                    Window.setRoot(Routes.stockManagementPage);
            } catch (EntityAlreadyExistsException __) {
                systemMessage.setText("Stock com ticker ja cadastrado!");
            } catch (Exception e) {
               systemMessage.setText("Desculpe, algo deu errado. Tente novamente mais tarde!");
                System.err.println(e);
            }
        } else {
           systemMessage.setText("Por favor, preencha todos os campos!");
        }
    }

    private boolean isFilledInputs() {
        return !txtTicker.getText().equals("") && !txtCompanyName.getText().equals("")
                && !txtCnpj.getText().equals("")  && !txtStockQuote.getText().equals("") && !cBoxStockType.getSelectionModel().isEmpty();
    }

    public void cancelRegister(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.stockManagementPage);
    }


}
