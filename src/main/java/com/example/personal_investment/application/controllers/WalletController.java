package com.example.personal_investment.application.controllers;

import com.example.personal_investment.application.common.Routes;
import com.example.personal_investment.application.view.Window;
import com.example.personal_investment.application.viewmodel.WalletVM;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.personal_investment.application.main.Main.searchWalletUC;
import static com.example.personal_investment.application.main.Main.deleteWalletUC;

public class WalletController {

    @FXML
    private Button btnAddWallet;

    @FXML
    private Button btnEditWallet;

    @FXML
    private Button btnDeleteWallet;

    @FXML
    private Button btnPreviousScreen;

    @FXML
    private Button btnSeeWallet;

    @FXML
    private Button btnLogout;

    @FXML
    private TableView<WalletVM> tbWallets;

    @FXML
    private TableColumn<WalletVM, String> cName;

    @FXML
    private TableColumn<WalletVM, String> cType;

    @FXML
    private TableColumn<WalletVM, String> cValue;

    @FXML
    private Label systemMessage;

    ObservableList<WalletVM> snapshot;

    @FXML
    private void initialize() {
        bindTableViewToItemsList();
        bindColumnsToValueSources();
        loadList();
    }

    private void loadList() {
        //mudar
        User user = new User("2", "Mylenna", "alaeatorio");
        List<WalletVM> wallets = searchWalletUC.findWalleyByUser(user).stream().map(
                WalletVM::new
        ).collect(Collectors.toList());
        snapshot.clear();
        snapshot.addAll(wallets);
    }

    private void bindColumnsToValueSources() {
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cValue.setCellValueFactory(new PropertyValueFactory<>("totalValue"));
        cType.setCellValueFactory(new PropertyValueFactory<>("stockType"));
    }

    private void bindTableViewToItemsList() {
        snapshot = FXCollections.observableArrayList();
        tbWallets.setItems(snapshot);
    }

    public void addWallet(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.addEditWalletPage);
        setScreenTypeUpdateOrInsertWallet(true);
    }

    public void updateWallet(ActionEvent actionEvent) throws IOException {
        if(isWalletSelect()){
            //mudar
            User user = new User("2", "Mylenna", "alaeatorio");
            WalletVM walletVM =  tbWallets.getSelectionModel().getSelectedItem();
            Wallet wallet = walletVM.setWalletEntity(user);
            Window.setRoot(Routes.addEditWalletPage);
            setWalletInUpdateWalletController(wallet);
            setScreenTypeUpdateOrInsertWallet(false);
        }else{
            systemMessage.setText("Carteira não selecionada");
        }
    }

    public void deleteWallet(ActionEvent actionEvent) {
        if(isWalletSelect()){
            //mudar
            User user = new User("2", "Mylenna", "alaeatorio");
            WalletVM walletVM =  tbWallets.getSelectionModel().getSelectedItem();
            Wallet wallet = walletVM.setWalletEntity(user);
            deleteWalletUC.delete(wallet);
            loadList();
        }else{
            systemMessage.setText("Carteira não selecionada");
        }
    }

    public void backPreviousScreen(ActionEvent actionEvent) {
        //qual e a tela anterior?
    }

    public void seeWallet(ActionEvent actionEvent) {
        //qual e a proxima tela?
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.loginPage);
    }

    private void setWalletInUpdateWalletController(Wallet wallet) throws IOException {
        AddEditWalletController controller = (AddEditWalletController) Window.getController();
        controller.setDataToUpdate(wallet);
    }

    private void setScreenTypeUpdateOrInsertWallet(Boolean screenType) {
        AddEditWalletController controller = (AddEditWalletController) Window.getController();
        controller.setDataTypeScreen(screenType);
    }

    private Boolean isWalletSelect(){
        return !tbWallets.getSelectionModel().isEmpty();
    }
}
