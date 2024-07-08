package com.example.esifolio2;

import com.example.esifolio2.InfosPersonnelles.GestionDeFichiersModele;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class StatsControleur extends InteractionsControleur implements GestionDeFichiersModele {
    @FXML private Button boutonDiagCircCompetences;
    @FXML private Button boutonDiagBatonCompetences;
    @FXML private Button boutonDiagCircProjets;
    @FXML private Button boutonDiagBatonProjets;
    @FXML private Label nbCompetences;
    @FXML private Label nbProjets;

    public void setNbProjets(int nbProjets){
        this.nbProjets.setText(Integer.toString(nbProjets));
    }
    public void setNbCompetences(int nbCompetences) {
        this.nbCompetences.setText(Integer.toString(nbCompetences));
    }

    @FXML public void diagBatonProjetsClicked(ActionEvent actionEvent) throws IOException{
        /** Passer à la page affichant le diagramme en bâtons des projets **/
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("StatsDiagBatonProjets.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),900.0,600.0);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        window.setScene(scene);
        window.show();
        window.centerOnScreen();

    }

    @FXML public void diagBatonProjetsSurvol() {
        boutonDiagBatonProjets.setStyle("-fx-background-color: #804BC6; -fx-background-radius: 8");
    }

    @FXML public void diagBatonProjetsExited() {
        boutonDiagBatonProjets.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 8");
    }

    @FXML public void diagBatonProjetsPressed() {
        boutonDiagBatonProjets.setStyle("-fx-background-color: #582B94; -fx-background-radius: 8");
    }


    @FXML public void diagCircProjetsClicked(ActionEvent actionEvent) throws IOException{
        /** Passer à la page affichant le diagramme circulaire des projets **/
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("StatsDiagCircProjets.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),900.0,600.0);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        window.centerOnScreen();
    }


    @FXML public void diagCircProjetsSurvol() {
        boutonDiagCircProjets.setStyle("-fx-background-color: #804BC6; -fx-background-radius: 8");
    }

    @FXML public void diagCircProjetsExited() {
        boutonDiagCircProjets.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 8");
    }

    @FXML public void diagCircProjetsPressed() {
        boutonDiagCircProjets.setStyle("-fx-background-color: #582B94; -fx-background-radius: 8");
    }

    @FXML public void diagBatonCompClicked(ActionEvent actionEvent) throws IOException{
        /** Passer à la page affichant le diagramme en bâtons des compétences **/
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("StatsDiagBatonCompetences.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),900.0,600.0);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        window.centerOnScreen();
    }

    @FXML public void diagBatonCompSurvol() {
        boutonDiagBatonCompetences.setStyle("-fx-background-color: #804BC6; -fx-background-radius: 8");
    }

    @FXML public void diagBatonCompExited() {
        boutonDiagBatonCompetences.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 8");
    }

    @FXML public void diagBatonCompPressed() {
        boutonDiagBatonCompetences.setStyle("-fx-background-color: #582B94; -fx-background-radius: 8");
    }

    @FXML public void diagCircCompClicked(ActionEvent actionEvent) throws IOException {
        /** Passer à la page affichant le diagramme circulaire des compétences **/
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("StatsDiagCircCompetences.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),900.0,600.0);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        window.centerOnScreen();
    }

    @FXML public void diagCircCompSurvol() {
        boutonDiagCircCompetences.setStyle("-fx-background-color: #804BC6; -fx-background-radius: 8");
    }

    @FXML public void diagCircCompExited() {
        boutonDiagCircCompetences.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 8");
    }

    @FXML public void diagCircCompPressed() {
        boutonDiagCircCompetences.setStyle("-fx-background-color: #582B94; -fx-background-radius: 8");
    }
}
