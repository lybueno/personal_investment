package com.example.personal_investment.application.controllers;

import com.example.personal_investment.application.common.Routes;
import com.example.personal_investment.application.common.UserSingleton;
import com.example.personal_investment.application.view.Window;
import com.example.personal_investment.domain.entities.user.User;
import com.example.personal_investment.domain.exceptions.EntityNotExistsException;
import com.example.personal_investment.domain.exceptions.IncorrectPasswordException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

import static com.example.personal_investment.application.main.Main.authenticateUserUC;

public class LoginController {

    @FXML
    private Label systemMessage;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnRegister;

    public void register(ActionEvent actionEvent) throws IOException {
        Window.setRoot(Routes.registerPage);
    }

    public void login(ActionEvent actionEvent) {
        if (isFilledInputs()) {
            try {
                User user = authenticateUserUC.login(txtUserName.getText(), txtPassword.getText());
                UserSingleton.login(user);
                Window.setRoot(Routes.stockManagementPage);
            } catch (IncorrectPasswordException __) {
                systemMessage.setText("Senha incorreta");
            } catch (EntityNotExistsException __) {
                systemMessage.setText("Nenhuma conta cadastrada com esse usuario");
            } catch (Exception e) {
                systemMessage.setText("Desculpe, algo deu errado. Tente novamente mais tarde!");
            }
        } else {
            systemMessage.setText("Por favor, preencha todos os campos!");
        }
    }

    private boolean isFilledInputs() {
        return !txtUserName.getText().equals("") && !txtPassword.getText().equals("");
    }
}
