//package com.example.personal_investment.application.controllers;
//
//import com.example.personal_investment.application.common.Routes;
//import com.example.personal_investment.application.view.Window;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//
//import java.io.IOException;
//
//public class ReportsController {
//
//    //table income tax
//    @FXML
//    private TableView<> tbIncomeTax;
//
//    @FXML
//    private TableColumn cIRCnpj;
//
//    @FXML
//    private TableColumn cIRDiscrimination;
//
//     @FXML
//    private TableColumn cIRLastYear;
//
//    @FXML
//    private TableColumn cIRCurrentYear;
//
//    @FXML
//    private TableColumn cIRCurrentDate;
//
//    @FXML
//    private TableColumn cIRLastDate;
//
//    //table darfs
//    @FXML
//    private TableView<> tbDarf;
//
//    @FXML
//    private TableColumn cDarfType;
//
//    @FXML
//    private TableColumn cDarfTaxAmount;
//
//    @FXML
//    private TableColumn cDarfSaleAmount;
//
//    @FXML
//    private TableColumn cDarfSaleValue;
//
//    @FXML
//    private TableColumn cDarfAvaragePurchaseValue;
//
//    @FXML
//    private TableColumn cDarfDueDate;
//
//    //table brokerage notes
//    @FXML
//    private TableView tbBrokerageNote;
//
//    @FXML
//    private TableColumn cNoteTicker;
//
//    @FXML
//    private TableColumn cNoteCnpj;
//
//    @FXML
//    private TableColumn cNoteCompany;
//
//    @FXML
//    private TableColumn cNoteQuantity;
//
//    @FXML
//    private TableColumn cNoteUnitaryValue;
//
//    @FXML
//    private TableColumn cNoteValue;
//
//    @FXML
//    private TableColumn cNoteType;
//
//    @FXML
//    private TableColumn cNoteDate;
//
//
//    @FXML
//    private Button btnLogout;
//    @FXML
//    private Button btnReturn;
//    @FXML
//    private Button btnView;
//
//    @FXML
//    private void initialize() {
//        bindTableViewToItemsList();
//        bindColumnsToValueSources();
//        loadLists();
//    }
//
//    private void loadLists() {
//    }
//
//    private void bindColumnsToValueSources() {
//    }
//
//    private void bindTableViewToItemsList() {
//    }
//
//
//    public void seeReport(ActionEvent actionEvent) {
//    }
//
//    public void backPreviousScreen(ActionEvent actionEvent) throws IOException {
//        Window.setRoot(Routes.investmentPage);
//    }
//
//    public void logout(ActionEvent actionEvent) throws IOException {
//        Window.setRoot(Routes.loginPage);
//    }
//}
