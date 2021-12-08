package com.example.personal_investment.application.controllers;

import com.example.personal_investment.application.common.Routes;
import com.example.personal_investment.application.common.UserSingleton;
import com.example.personal_investment.application.view.Window;
import com.example.personal_investment.domain.entities.stock.Stock;
import com.example.personal_investment.domain.entities.stock_transaction.StockTransaction;
import com.example.personal_investment.domain.entities.stock_transaction.TransactionType;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.exceptions.AmountNotAllowedException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.personal_investment.application.main.Main.*;

public class StockTransactionController {

    @FXML
    private ComboBox<String> cBoxWallet;

    @FXML
    private Button btnConfirmTransaction;

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
    private Label lbTitle;

    private User user;
    private Stock stock;
    private Wallet wallet;
    private TransactionType transactionType;

    public void setData(Wallet wallet, Stock stock, TransactionType transactionType) throws IOException {
        this.user = UserSingleton.getInstance().getUser();
        if (user == null) {
            Window.setRoot(Routes.loginPage);
        }

        if (stock == null) {
            Window.setRoot(Routes.stockManagementPage);
        }
        this.stock = stock;
        this.wallet = wallet;
        this.transactionType = transactionType;

        loadScreen();
    }

    private void loadScreen() {
        loadComboBox();
        loadFields();
    }

    private void loadFields() {
        lbTicker.setText(stock.getTicker());
        lbCnpj.setText(stock.getCnpj());
        lbCompanyName.setText(stock.getCompanyName());
        lbTitle.setText(transactionType == TransactionType.PURCHASE ?
                "Cadastrar Compra de Ação" :
                "Cadastrar Venda de Ação"
        );
        if (wallet != null) {
            cBoxWallet.setValue(wallet.getName());
            cBoxWallet.setDisable(true);
        }
    }

    private void loadComboBox() {
        List<Wallet> listWallets = searchWalletUC.findWalletByUser(user);

        List<String> walletNames = listWallets.stream()
                .filter(w -> w.getType() == stock.getType())
                .map(Wallet::getName)
                .collect(Collectors.toList());

        cBoxWallet.getItems().setAll(walletNames);
    }

    public void confirmTransaction(ActionEvent actionEvent) {
        if (isFilledTextFields()) {
            if (transactionType == TransactionType.PURCHASE) {
                registerPurchase();
            } else if (transactionType == TransactionType.SALE) {
                registerSale();
            }
        } else {
            systemMessage.setText("Campos não preenchidos");
        }
    }

    private void registerSale() {
        try {
            StockTransaction stockTransaction = createStockTransactionWithInputFields();
            BigDecimal tax = calculateTaxAmountUC.calculate(stockTransaction);
            if (tax.intValue() > 0) {
                showAlertAndCancelTransactionIfUserRequest(tax);
            }
            registerStockSaleUC.sell(stockTransaction, tax);
            Window.setRoot(Routes.investmentManagementPage);
            setWalletInInvestmentPage(stockTransaction.getWallet());
        } catch (AmountNotAllowedException __) {
            systemMessage.setText("Erro, você está tentando vender uma quantia maior do que possui");
        } catch (Exception e) {
            systemMessage.setText("Ocorreu algum erro, tente novamente mais tarde");
            e.printStackTrace();
        }
    }

    private void showAlertAndCancelTransactionIfUserRequest(BigDecimal tax) {
        ButtonType confirm = new ButtonType("Continuar Compra");
        ButtonType reject = new ButtonType("Desistir");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Deseja continuar?",reject,confirm);
        alert.setTitle("Transação de Ação");
        alert.setResizable(true);
        alert.setHeaderText("Imposto");
        alert.setContentText("Ao realizar essa transação, você irá pagar um imposto de " + tax + "\nDeseja continuar?");
        alert.showAndWait().ifPresent(res -> {
            if (res == reject) {
                try {
                    Window.setRoot(Routes.stockManagementPage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void registerPurchase() {
        try {
            StockTransaction stockTransaction = createStockTransactionWithInputFields();
            registerStockPurchaseUC.purchase(stockTransaction);

            Window.setRoot(Routes.investmentManagementPage);
            setWalletInInvestmentPage(stockTransaction.getWallet());
        } catch (Exception e) {
            systemMessage.setText("Ocorreu algum erro, tente novamente mais tarde");
            System.out.println(e);
        }
    }

    private StockTransaction createStockTransactionWithInputFields() throws IOException {
        if (wallet == null) {
            Optional<Wallet> optional = searchWalletUC
                    .findWalletByUser(user).stream()
                    .filter(w -> w.getName().equals(cBoxWallet.getValue())).findFirst();
            if (optional.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Transação de Ação");
                alert.setHeaderText("Erro, ocorreu algum erro com a carteira");
                alert.setContentText("Desculpe, tente novamente mais tarde");
                alert.showAndWait();

                Window.setRoot(Routes.stockManagementPage);
            } else {
                wallet = optional.get();
            }
        }

        return new StockTransaction(
                stock,
                wallet,
                dPicker.getValue(),
                Integer.parseInt(txtQuantity.getText()),
                new BigDecimal(txtUnitaryValue.getText()),
                transactionType
        );

    }

    public void cancelRegister(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.stockManagementPage);
    }

    private Boolean isFilledTextFields() {
        return !txtQuantity.getText().equals("") && !txtUnitaryValue.getText().equals("") && !(dPicker.getValue() == null)
                && cBoxWallet.getValue() != null;
    }

    private void setWalletInInvestmentPage(Wallet wallet) throws IOException {
        InvestmentsManagementController controller = (InvestmentsManagementController) Window.getController();
        controller.setDataWallet(wallet);
    }
}