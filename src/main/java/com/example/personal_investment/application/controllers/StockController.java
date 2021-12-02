package com.example.personal_investment.application.controllers;

import com.example.personal_investment.application.common.Routes;
import com.example.personal_investment.application.common.UIMode;
import com.example.personal_investment.application.view.Window;
import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.exceptions.EntityAlreadyExistsException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.personal_investment.application.main.Main.addStockUC;
import static com.example.personal_investment.application.main.Main.updateStockUC;


public class StockController {
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField txtCompanyName;
    @FXML
    private TextField txtCnpj;
    @FXML
    private TextField txtStockQuote;
    @FXML
    private TextField txtTicker;
    @FXML
    private ComboBox<String> cBoxStockType;
    @FXML
    private Label systemMessage;

    private UIMode uiMode;
    private Stock stock;

    public void setData(Stock stock, UIMode uiMode) throws IOException {
        this.stock = stock;
        this.uiMode = uiMode;

        // TODO(daniel): Implementar 3 estados da pagina(detalhar,atualizar e adicionar)
        configPageForEachUIMode();
    }

    private void configPageForEachUIMode() {
        fillInputsWithEntityIfNotNull();

        if (uiMode == UIMode.DETAIL) {
            txtTicker.setDisable(true);
            txtCnpj.setDisable(true);
            txtCompanyName.setDisable(true);
            txtStockQuote.setDisable(true);
            cBoxStockType.setDisable(true);
        } else if (uiMode == UIMode.UPDATE) {
            btnRegister.setText("Atualizar");
            cBoxStockType.setDisable(true);
        }
    }

    private void fillInputsWithEntityIfNotNull() {
        if (stock != null) {
            cBoxStockType.setValue(stock.getType().toString());
            txtCnpj.setText(stock.getCnpj());
            txtCompanyName.setText(stock.getCompanyName());
            txtTicker.setText(stock.getTicker());
            txtStockQuote.setText(stock.getStockQuote().toString());
        }
    }

    @FXML
    private void initialize() {
        List<String> types = Arrays.stream(StockType.values())
                .map(StockType::toString)
                .collect(Collectors.toList());

        cBoxStockType.getItems().setAll(types);
    }

    public void registerStock(ActionEvent actionEvent) {
        if (isFilledInputs()) {
            if (uiMode == UIMode.INSERT) {
                registerNewStock();
            } else {
                updateStock();
            }
        } else {
            systemMessage.setText("Por favor, preencha todos os campos!");
        }

    }

    private void updateStock() {
        try {
            fillEntityWithInputFields();
            updateStockUC.update(stock);
            Window.setRoot(Routes.stockManagementPage);
        } catch (Exception e) {
            systemMessage.setText("Desculpe, algo deu errado. Tente novamente mais tarde!");
            System.err.println(e);
        }
    }

    private void registerNewStock() {
        try {
            fillEntityWithInputFields();
            addStockUC.add(stock);
            Window.setRoot(Routes.stockManagementPage);
        } catch (EntityAlreadyExistsException __) {
            systemMessage.setText("Stock com ticker ja cadastrado!");
        } catch (Exception e) {
            systemMessage.setText("Desculpe, algo deu errado. Tente novamente mais tarde!");
            System.err.println(e);
        }
    }

    private void fillEntityWithInputFields() {
        if (stock == null) {
            stock = new Stock(
                    StockType.toEnum(cBoxStockType.getValue()),
                    txtTicker.getText(),
                    txtCompanyName.getText(),
                    txtCnpj.getText(),
                    new BigDecimal(txtStockQuote.getText())
            );
        }
        stock.setTicker(txtTicker.getText());
        stock.setCompanyName(txtCompanyName.getText());
        stock.setCnpj(txtCnpj.getText());
        stock.setStockQuote(new BigDecimal(txtStockQuote.getText()));
    }

    private boolean isFilledInputs() {
        return !txtTicker.getText().equals("") && !txtCompanyName.getText().equals("")
                && !txtCnpj.getText().equals("") && !txtStockQuote.getText().equals("") && !cBoxStockType.getSelectionModel().isEmpty();
    }

    public void cancelRegister(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.stockManagementPage);
    }
}
