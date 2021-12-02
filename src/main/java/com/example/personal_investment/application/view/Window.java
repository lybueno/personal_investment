package com.example.personal_investment.application.view;

import com.example.personal_investment.application.common.Routes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Window extends Application {
    private static Scene scene;
    private static Object controller;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML(Routes.stockManagementPage));
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = fxmlLoader.load(Window.class.getResource(fxml + ".fxml").openStream());
        controller = fxmlLoader.getController();
        return parent;
    }

    public static Object getController() {
        return controller;
    }

    public static void main(String[] args) {
        launch();
    }
}