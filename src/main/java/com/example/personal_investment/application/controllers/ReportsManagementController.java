package com.example.personal_investment.application.controllers;

import com.example.personal_investment.application.common.Routes;
import com.example.personal_investment.application.common.UserSingleton;
import com.example.personal_investment.application.view.Window;
import com.example.personal_investment.application.viewmodel.BrokerageNoteVM;
import com.example.personal_investment.application.viewmodel.DarfVM;
import com.example.personal_investment.application.viewmodel.IncomeTaxVM;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.entities.wallet.Wallet;
import com.example.personal_investment.domain.usecases.report.BrokerageNoteReportUC;
import com.example.personal_investment.domain.usecases.report.DarfReportUC;
import com.example.personal_investment.domain.usecases.report.IncomeTaxReportUC;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.personal_investment.application.main.Main.*;

public class ReportsManagementController {

    //table income tax
    @FXML
    private TableView<IncomeTaxVM> tbIncomeTax;

    @FXML
    private TableColumn<IncomeTaxVM, String> cIRCnpj;

    @FXML
    private TableColumn<IncomeTaxVM, String> cIRDiscrimination;

     @FXML
    private TableColumn<IncomeTaxVM, String> cIRLastYear;

    @FXML
    private TableColumn<IncomeTaxVM, String> cIRCurrentYear;

    @FXML
    private TableColumn<IncomeTaxVM, String> cIRCurrentDate;

    @FXML
    private TableColumn<IncomeTaxVM, String> cIRLastDate;

    //table darf
    @FXML
    private TableView<DarfVM> tbDarf;

    @FXML
    private TableColumn<DarfVM, String> cDarfType;

    @FXML
    private TableColumn<DarfVM, String> cDarfTaxAmount;

    @FXML
    private TableColumn<DarfVM, String> cDarfSaleValue;

    @FXML
    private TableColumn<DarfVM, String> cDarfDueDate;

    @FXML
    private TableColumn<DarfVM, String> cDarfAveragePurchaseValue;


    //table brokerage notes
    @FXML
    private TableView<BrokerageNoteVM> tbBrokerageNote;

    @FXML
    private TableColumn<BrokerageNoteVM, String> cNoteTicker;

    @FXML
    private TableColumn<BrokerageNoteVM, String> cNoteCnpj;

    @FXML
    private TableColumn<BrokerageNoteVM, String> cNoteCompany;

    @FXML
    private TableColumn<BrokerageNoteVM, String> cNoteQuantity;

    @FXML
    private TableColumn<BrokerageNoteVM, String> cNoteUnitaryValue;

    @FXML
    private TableColumn<BrokerageNoteVM, String> cNoteValue;

    @FXML
    private TableColumn<BrokerageNoteVM, String> cNoteType;

    @FXML
    private TableColumn<BrokerageNoteVM, String> cNoteDate;

    private ObservableList<IncomeTaxVM> snapshotIR;
    private ObservableList<BrokerageNoteVM> snapshotNote;
    private ObservableList<DarfVM> snapshotDarf;

    @FXML
    private Button btnLogout;
    @FXML
    private Button btnReturn;
    @FXML
    private Button btnView;
    @FXML
    private Label username;

    private User user;

    private ObservableList<Wallet> wallets;

    @FXML
    private void initialize() throws IOException {
        user = UserSingleton.getInstance().getUser();
        if (user == null) {
            Window.setRoot(Routes.loginPage);
        }
        username.setText(user.getUsername());
        loadWalletsUser();
        bindTableViewToItemsList();
        bindColumnsToValueSources();
        loadLists();
    }


    private void loadWalletsUser(){
        wallets = FXCollections.observableArrayList();
        wallets.clear();
        wallets.addAll(searchWalletUC.findWalletByUser(user));
    }

    private void loadLists() {
        loadListDarf();
        loadListNote();
        loadListIR();
    }

    private void bindColumnsToValueSources() {
        bindColumsToValuesSourcesDarf();
        bindColumsToValuesSourcesNote();
        bindColumsToValuesSourcesIR();
    }

    private void bindTableViewToItemsList() {
        bindTableViewToItemsListDarf();
          bindTableViewToItemsListNote();
          bindTableViewToItemsListIR();
    }

    public void seeReport(ActionEvent actionEvent) {
        DarfVM selectedDarf = tbDarf.getSelectionModel().getSelectedItem();
        BrokerageNoteVM selectedNote = tbBrokerageNote.getSelectionModel().getSelectedItem();
        IncomeTaxVM selectedIR = tbIncomeTax.getSelectionModel().getSelectedItem();

        seeDarf(selectedDarf);
        seeNote(selectedNote);
        seeIR(selectedIR);

    }

    private void loadListIR() {
        List<IncomeTaxVM> incomeTax = new ArrayList<>();

        for (Wallet w : wallets) {
            searchBrokerageNoteUC.findAllByWallet(w.getId()).forEach(
                    note -> {
                        BigDecimal valueLastYear;
                        LocalDate lastDate = LocalDate.now().minusYears(1);

                        if (note.getTransactionDate().isAfter(lastDate)) {
                            valueLastYear = new BigDecimal("0");
                        } else{
                            valueLastYear = note.getUnitaryValue();
                        }
                        incomeTax.add(new IncomeTaxVM(note,note.calculateTransactionAmount(),valueLastYear));
                    }
            );
        }

        snapshotIR.clear();
        snapshotIR.addAll(incomeTax);
    }

    private void loadListNote() {

        List<BrokerageNoteVM> brokerageNotes = new ArrayList<>();
        for(Wallet w : wallets){
            searchBrokerageNoteUC.findAllByWallet(w.getId()).stream().forEach(
                    note ->{
                        brokerageNotes.add(new BrokerageNoteVM(note.getStock().getTicker(),
                                note.calculateTransactionAmount().toString(),
                                note.getTransactionDate(),
                                note.getQuantity().toString(),
                                note.getUnitaryValue().toString(),
                                note.getTransactionType().toString(),
                                note.getStock().getCnpj(),
                                note.getStock().getCompanyName(),
                                note.getWallet().getUser().getUsername()));

                    }

            );
        }

        snapshotNote.clear();
        snapshotNote.addAll(brokerageNotes);
    }

    private void loadListDarf() {
        List<DarfVM> darfs = searchDarfUC.findAllByUserName(user.getUsername()).stream().map(
                DarfVM::new
        ).collect(Collectors.toList());
        snapshotDarf.clear();
        snapshotDarf.addAll(darfs);
    }

    private void bindColumsToValuesSourcesIR() {
        cIRCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        cIRDiscrimination.setCellValueFactory(new PropertyValueFactory<>("discrimination"));
        cIRLastYear.setCellValueFactory(new PropertyValueFactory<>("lastYear"));
        cIRCurrentYear.setCellValueFactory(new PropertyValueFactory<>("currentYear"));
        cIRCurrentDate.setCellValueFactory(new PropertyValueFactory<>("currentDate"));
        cIRLastDate.setCellValueFactory(new PropertyValueFactory<>("lastDate"));
    }

    private void bindColumsToValuesSourcesDarf(){
        cDarfType.setCellValueFactory(new PropertyValueFactory<>("stockType"));
        cDarfTaxAmount.setCellValueFactory(new PropertyValueFactory<>("taxAmount"));
        cDarfSaleValue.setCellValueFactory(new PropertyValueFactory<>("saleValue"));
        cDarfAveragePurchaseValue.setCellValueFactory(new PropertyValueFactory<>("averagePurchaseValue"));
        cDarfDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
    }

    private void bindColumsToValuesSourcesNote(){
        cNoteTicker.setCellValueFactory(new PropertyValueFactory<>("ticker"));
        cNoteValue.setCellValueFactory(new PropertyValueFactory<>("transactionAmount"));
        cNoteDate.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        cNoteQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        cNoteUnitaryValue.setCellValueFactory(new PropertyValueFactory<>("unitaryValue"));
        cNoteType.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
        cNoteCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        cNoteCompany.setCellValueFactory(new PropertyValueFactory<>("companyName"));
    }

    private void bindTableViewToItemsListIR(){
        snapshotIR = FXCollections.observableArrayList();
        tbIncomeTax.setItems(snapshotIR);
    }

    private void bindTableViewToItemsListNote(){
        snapshotNote = FXCollections.observableArrayList();
        tbBrokerageNote.setItems(snapshotNote);
    }

    private void bindTableViewToItemsListDarf(){
        snapshotDarf = FXCollections.observableArrayList();
        tbDarf.setItems(snapshotDarf);
    }

    public void seeIR(IncomeTaxVM selectedIR){
        IncomeTaxReportUC incomeTaxReportUC = new IncomeTaxReportUC();

        if (selectedIR != null) {
            incomeTaxReportUC.printIR(selectedIR);
            tbIncomeTax.getSelectionModel().clearSelection();
        }

    }

    public void seeDarf(DarfVM selectedDarf){
        DarfReportUC darfReportUC = new DarfReportUC();

        if (selectedDarf != null) {
            darfReportUC.printDarf(selectedDarf, user);
            tbDarf.getSelectionModel().clearSelection();
        }
    }

    public void seeNote(BrokerageNoteVM selectedNote){
        BrokerageNoteReportUC brokerageNoteReportUC = new BrokerageNoteReportUC();

        if (selectedNote != null) {
            brokerageNoteReportUC.printTradingNote(selectedNote);
            tbBrokerageNote.getSelectionModel().clearSelection();
        }
    }

    public void backPreviousScreen(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.stockManagementPage);
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.loginPage);
    }
}
