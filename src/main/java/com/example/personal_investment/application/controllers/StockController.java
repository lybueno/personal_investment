package com.example.personal_investment.application.controllers;

import com.example.personal_investment.application.common.Routes;
import com.example.personal_investment.application.common.UIMode;
import com.example.personal_investment.application.view.Window;
import com.example.personal_investment.application.viewmodel.StockVM;
import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.exceptions.EntityAlreadyExistsException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.personal_investment.application.main.Main.addStockUC;


public class StockController {
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
    private StockVM stockVM;

    public void setData(StockVM stock, UIMode uiMode) throws IOException {
        this.stockVM = stock;
        this.uiMode = uiMode;

        // TODO(daniel): Implementar 3 estados da pagina(detalhar,atualizar e adicionar)
        configPageForEachUIMode();
    }

    private void configPageForEachUIMode() {


        if(uiMode == UIMode.DETAIL){
            // Desativar inputs
        } else if(uiMode == UIMode.UPDATE){
            // fazer configs necessarias
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
