package com.example.esifolio2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;


public class Main extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("BienvenueVue.fxml"));
        Scene sceneInfoPerso = new Scene(fxmlLoader.load(), 900.0, 600.0);
        FileInputStream input = new FileInputStream(Paths.get("src/main/resources/com/example/esifolio2/images/IconeApp.png").toFile());
        stage.getIcons().add(new Image(input));
        stage.setTitle("Esi Folio");
        stage.setWidth(920.0);
        stage.setHeight(640.0);
        stage.setResizable(false);
        stage.setScene(sceneInfoPerso);
        stage.show();
        stage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch();
    }
}