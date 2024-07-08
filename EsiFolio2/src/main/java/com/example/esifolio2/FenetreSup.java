package com.example.esifolio2;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FenetreSup {

    static boolean reponse;

    public static boolean display(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Suppression");
        window.setWidth(400);
        window.setHeight(170);
        Label label = new Label("Etes-vous sûr de supprimer le projet ?\n           Si oui, actualisez la page.");
        label.setFont(new Font("Comfortaa Medium",16));
        Button bouttonSupprimer = new Button("Supprimer");
        Button bouttonAnnuler = new Button("Annuler");
        bouttonSupprimer.setPrefWidth(150);
        bouttonSupprimer.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 12; -fx-text-fill: white");
        bouttonSupprimer.setFont(new Font("Comfortaa SemiBold",14));
        bouttonSupprimer.setCursor(Cursor.HAND);
        bouttonAnnuler.setPrefWidth(150);
        bouttonAnnuler.setStyle("-fx-background-color: #C4C4C4; -fx-background-radius: 12");
        bouttonAnnuler.setFont(new Font("Comfortaa SemiBold",14));
        bouttonAnnuler.setCursor(Cursor.HAND);
        bouttonSupprimer.setOnAction(actionEvent -> {
            reponse = true;
            window.close();
        });
        bouttonAnnuler.setOnAction(actionEvent -> {
            reponse = false;
            window.close();
        });
        HBox hbox = new HBox();
        hbox.setSpacing(20);
        hbox.getChildren().addAll(bouttonAnnuler,bouttonSupprimer);
        hbox.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(20);
        vBox.getChildren().addAll(label,hbox);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();
        window.centerOnScreen();
        return reponse;
    }
}
