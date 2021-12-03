package com.example.personal_investment.application.controllers;

import com.example.personal_investment.application.common.Routes;
import com.example.personal_investment.application.common.UIMode;
import com.example.personal_investment.application.view.Window;
import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.example.personal_investment.application.main.Main.addWalletUC;
import static com.example.personal_investment.application.main.Main.updateWalletUC;

public class AddEditWalletController {
    @FXML
    private TextField txtName;

    @FXML
    private ComboBox<StockType> cbStockType;

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnCancel;

    @FXML
    private Label systemMessage;

    private ObservableList<StockType> stockTypes = FXCollections.observableArrayList();

    private Wallet wallet;
    //true- cadatro de carteira
    //false- editar carteira
    private UIMode screenType;

    public void setDataToUpdate(Wallet wallet) throws IOException {
        if(wallet == null){
            Window.setRoot(Routes.walletManagementPage);
        }
        this.wallet = wallet;
    }

    public void setDataTypeScreen(UIMode typeScreen){
        this.screenType = typeScreen;
        loadScreen();
    }

    @FXML
    private void initialize() {
        loadComboBox();
    }

    private void loadScreen(){
        if(screenType == UIMode.INSERT){
            btnRegister.setText("Cadastrar");
        }else if(screenType == UIMode.UPDATE){
            txtName.setText(wallet.getName());
            cbStockType.setValue(wallet.getType());
            btnRegister.setText("Editar");
        }
    }

    public void backPreviousScreen(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.walletManagementPage);
    }

    public void registerOrUpdateWallet(ActionEvent actionEvent) throws IOException {
        if(isFilledInputs()){
            if(screenType == UIMode.INSERT){
                addWallet();
            }else if(screenType == UIMode.UPDATE){
                updateWallet();
            }
            Window.setRoot(Routes.walletManagementPage);
        }else{
            systemMessage.setText("Campos inseridos estão vazios");
        }

    }

    private void addWallet(){
        try{
            String nameWallet = txtName.getText();
            StockType stockType = cbStockType.getSelectionModel().getSelectedItem();
            //mudar, e apenas para teste
            User user = new User("2", "Mylenna", "alaeatorio");
            Wallet newWallet = new Wallet(nameWallet, stockType, user);
            addWalletUC.insert(newWallet);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void updateWallet(){
        try{
            String nameWallet = txtName.getText();
            StockType stockType = cbStockType.getSelectionModel().getSelectedItem();
            //mudar, e apenas para teste
            User user = new User("2", "Mylenna", "alaeatorio");
            Wallet updateWallet = new Wallet(wallet.getId(), nameWallet, stockType, user);
            updateWalletUC.update(updateWallet);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void loadComboBox(){
        stockTypes.clear();

        List<StockType> listStockTypes = Arrays.asList(StockType.values());

        stockTypes.addAll(listStockTypes);
        cbStockType.setItems(stockTypes);
    }

    private boolean isFilledInputs() {
        return !txtName.getText().equals("") && !cbStockType.getSelectionModel().isEmpty();
    }
}
