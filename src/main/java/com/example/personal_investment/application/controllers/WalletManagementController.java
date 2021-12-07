package com.example.personal_investment.application.controllers;

import com.example.personal_investment.application.common.Routes;
import com.example.personal_investment.application.common.UIMode;
import com.example.personal_investment.application.common.UserSingleton;
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

import static com.example.personal_investment.application.main.Main.deleteWalletUC;
import static com.example.personal_investment.application.main.Main.searchWalletUC;

public class WalletManagementController {

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

    private User user;

//    public void setDat(User user) throws IOException {
//        if(user == null){
//            Window.setRoot(Routes.loginPage);
//        }
//        this.user = user;
//        loadList();
//    }

    @FXML
    private void initialize() throws IOException {
        loadUserLogged();
        bindTableViewToItemsList();
        bindColumnsToValueSources();
        loadList();
    }

    private void loadList() {
        List<WalletVM> wallets = searchWalletUC.findWalletByUser(user).stream().map(WalletVM::new).collect(Collectors.toList());
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
        Window.setRoot(Routes.walletPage);
        setScreenTypeUpdateOrInsertWallet(UIMode.INSERT);
        setUserAddEditWalletPage();
    }

    public void updateWallet(ActionEvent actionEvent) throws IOException {
        if(isWalletSelect()){
            WalletVM walletVM =  tbWallets.getSelectionModel().getSelectedItem();
            Wallet wallet = walletVM.toWalletEntity(user);
            Window.setRoot(Routes.walletPage);
            setWalletInUpdateWalletController(wallet);
            setScreenTypeUpdateOrInsertWallet(UIMode.UPDATE);
            setUserAddEditWalletPage();
        }else{
            systemMessage.setText("Carteira não selecionada");
        }
    }

    public void deleteWallet(ActionEvent actionEvent) {
        if(isWalletSelect()){
            WalletVM walletVM = tbWallets.getSelectionModel().getSelectedItem();
            Wallet wallet = walletVM.toWalletEntity(user);
            deleteWalletUC.delete(wallet);
            loadList();
        }else{
            systemMessage.setText("Carteira não selecionada");
        }
    }

    public void backPreviousScreen(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.stockManagementPage);
    }

    public void investmentsPage(ActionEvent actionEvent) throws IOException {
        if(isWalletSelect()){
            WalletVM walletVM =  tbWallets.getSelectionModel().getSelectedItem();
            Wallet wallet = walletVM.toWalletEntity(user);
            Window.setRoot(Routes.investmentManagementPage);
            setWalletInInvestmentPage(wallet);
        }else{
            systemMessage.setText("Carteira não selecionada");
        }
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.loginPage);
    }

    private void setWalletInUpdateWalletController(Wallet wallet) throws IOException {
        WalletController controller = (WalletController) Window.getController();
        controller.setDataToUpdate(wallet);
    }

    private void setScreenTypeUpdateOrInsertWallet(UIMode uiMode) {
        WalletController controller = (WalletController) Window.getController();
        controller.setDataTypeScreen(uiMode);
    }

    private Boolean isWalletSelect(){
        return !tbWallets.getSelectionModel().isEmpty();
    }

    public void setUserAddEditWalletPage() throws IOException {
        WalletController controller = (WalletController) Window.getController();
        controller.setData(user);
    }

    public void setWalletInInvestmentPage(Wallet wallet) throws IOException {
        InvestmentsManagementController controller = (InvestmentsManagementController) Window.getController();
        controller.setDataWallet(wallet);
    }

    private void loadUserLogged() throws IOException {
        user = UserSingleton.getInstance().getUser();
        if (user == null) {
            Window.setRoot(Routes.loginPage);
        }
    }
}
