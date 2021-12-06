package com.example.personal_investment.application.controllers;

import com.example.personal_investment.application.common.Routes;
import com.example.personal_investment.application.common.UserSingleton;
import com.example.personal_investment.application.view.Window;
import com.example.personal_investment.domain.entities.investment.Investment;
import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock.StockType;
import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.entities.stock_transaction.TransactionType;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.personal_investment.application.main.Main.registerStockPurchaseUC;
import static com.example.personal_investment.application.main.Main.searchWalletUC;

public class InvestmentController {

    @FXML
    private ComboBox<String> cBoxWallet;

    @FXML
    private Button btnBuy;

    @FXML
    private Button btnCancel;

    @FXML
    private Label systemMessage;

    @FXML
    private TextField txtQuantity;

    @FXML
    private Label lbTicker;

    @FXML
    private Label lbCompanyName;

    @FXML
    private Label lbCnpj;

    @FXML
    private DatePicker dPicker;

    @FXML
    private TextField txtUnitaryValue;

    @FXML
    private Label lbTittle;

    private User user;

    private Stock stock;
    private Wallet wallet;
    private TransactionType transactionType;

    public void setData(Wallet wallet, Stock stock, TransactionType transactionType) throws IOException {
        if(stock == null){
            Window.setRoot(Routes.stockManagementPage);
        }
        this.stock = stock;
        this.wallet = wallet;
        this.transactionType = transactionType;

        this.user = UserSingleton.getInstance().getUser();
        if(user == null){
            Window.setRoot(Routes.loginPage);
        }

        loadScreen();
    }

    private void loadScreen(){
        loadComboBox();
        loadFields();
    }

    private void loadFields() {
        lbTicker.setText(stock.getTicker());
        lbCnpj.setText(stock.getCnpj());
        lbCompanyName.setText(stock.getCompanyName());
        lbTittle.setText(transactionType == TransactionType.PURCHASE ?
                "Cadastrar Compra de Ação" :
                "Cadastrar Venda de Ação"
        );
        if(wallet != null) {
            cBoxWallet.setValue(wallet.getName());
            cBoxWallet.setDisable(true);
        }
    }

    private void loadComboBox(){
        List<Wallet> listWallets = searchWalletUC.findWalletByUser(user);

        List<String> walletNames = listWallets.stream()
                .filter(w -> w.getType() == stock.getType())
                .map(Wallet::getName)
                .collect(Collectors.toList());
        // TODO: se não houver carteiras, sair da pagina
        cBoxWallet.getItems().setAll(walletNames);
    }

    public void confirmTransaction(ActionEvent actionEvent) {
        if(isFilledTextFields()){
            StockTransaction stockTransaction = createStockTransactionWithInputFields();
            if(transactionType == TransactionType.PURCHASE){
                try {
                    registerStockPurchaseUC.purchase(stockTransaction);
                }catch (Exception e){
                    System.out.println(e.toString());
                }
            }
        }else{
            systemMessage.setText("Campos não preenchidos");
        }
    }

    private StockTransaction createStockTransactionWithInputFields() {
        return new StockTransaction(
                stock,
                wallet, // TODO: resolver erro de wallet null
                dPicker.getValue(),
                Integer.parseInt(txtQuantity.getText()),
                new BigDecimal(txtUnitaryValue.getText()),
                transactionType
        );

    }

    public void cancelRegister(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.investmentManagementPage);
    }

    private Boolean isFilledTextFields(){
        return !txtQuantity.getText().equals("") && !txtUnitaryValue.getText().equals("") && !(dPicker.getValue() == null);
    }
}
