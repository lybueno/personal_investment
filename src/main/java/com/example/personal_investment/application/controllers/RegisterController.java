package com.example.personal_investment.application.controllers;

import com.example.personal_investment.application.common.Routes;
import com.example.personal_investment.application.common.UserSingleton;
import com.example.personal_investment.application.view.Window;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.exceptions.EntityAlreadyExistsException;
import com.example.personal_investment.domain.exceptions.IncorrectPasswordException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

import static com.example.personal_investment.application.main.Main.registerUserUC;

public class RegisterController {

    @FXML
    private Label systemMessage;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtConfirmPassword;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnCancel;


    public void signup(ActionEvent actionEvent) {
        if (isFilledInputs()) {
            try {
                User user = registerUserUC.signUp(txtUserName.getText(), txtPassword.getText(), txtConfirmPassword.getText());
                UserSingleton.login(user);
                Window.setRoot(Routes.stockManagementPage);
            } catch (IncorrectPasswordException __) {
                systemMessage.setText("Senhas diferentes! Verifique se digitou as senhas corretamente");
            } catch (EntityAlreadyExistsException __) {
                systemMessage.setText("Usuario com nome ja cadastrado!");
            } catch (Exception e) {
                systemMessage.setText("Desculpe, algo deu errado. Tente novamente mais tarde!");
                System.err.println(e);
            }
        } else {
            systemMessage.setText("Por favor, preencha todos os campos!");
        }
    }

    public void cancelRegister(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.loginPage);
    }

    private boolean isFilledInputs() {
        return !txtUserName.getText().equals("") && !txtPassword.getText().equals("")
                && !txtConfirmPassword.getText().equals("");
    }
}
