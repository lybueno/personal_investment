package com.example.personal_investment.application.controllers;

import com.example.personal_investment.application.common.Routes;
import com.example.personal_investment.application.view.Window;
import com.example.personal_investment.application.viewmodel.BrokerageNoteVM;
import com.example.personal_investment.application.viewmodel.DarfVM;
import com.example.personal_investment.application.viewmodel.IncomeTaxVM;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.usecases.report.DarfReportUC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.personal_investment.application.main.Main.*;

public class ReportsController {

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

    //table darfs criar uma viewmodel?
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
    private void initialize() {
        bindTableViewToItemsList();
        bindColumnsToValueSources();
        loadLists();
    }

    private void loadLists() {
      loadListDarf();
      //  loadListNote();
        //falta loadIR
    }

    //ver como pegar os relatorios
    private void loadListIR() {

    }

    private void loadListNote() {
        List<BrokerageNoteVM> brokerageNotes = searchBrokerageNoteUC.findAll().stream().map(
                BrokerageNoteVM::new
        ).collect(Collectors.toList());
        snapshotNote.clear();
        snapshotNote.addAll(brokerageNotes);
    }

    private void loadListDarf() {
        List<DarfVM> darfs = searchDarfUC.findAll().stream().map(
                DarfVM::new
        ).collect(Collectors.toList());
        snapshotDarf.clear();
        snapshotDarf.addAll(darfs);
    }

    private void bindColumnsToValueSources() {
   //     bindColumsToValuesSourcesIR();
     //   bindColumsToValuesSourcesNote();
        bindColumsToValuesSourcesDarf();
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
        cNoteCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        cNoteCompany.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        cNoteQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        cNoteCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        cNoteUnitaryValue.setCellValueFactory(new PropertyValueFactory<>("unitaryValue"));
        cNoteValue.setCellValueFactory(new PropertyValueFactory<>("transactionAmount"));
        cNoteType.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
        cNoteDate.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
    }

    private void bindTableViewToItemsList() {
       // bindTableViewToItemsListIR();
     //   bindTableViewToItemsListNote();
        bindTableViewToItemsListDarf();
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

    public void seeReport(ActionEvent actionEvent) throws InterruptedException {
        DarfReportUC darfReportUC = new DarfReportUC();
        DarfVM selectedDarf = tbDarf.getSelectionModel().getSelectedItem();

        // TODO user usado so para teste, mudar depois
        Optional<User> user = findUserUseCase.findOneByUsername("mylla");

        if (selectedDarf != null) {
            darfReportUC.printDarf(selectedDarf,user.get()) ;
        }
    }

    public void backPreviousScreen(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.investmentPage);
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.loginPage);
    }
}
