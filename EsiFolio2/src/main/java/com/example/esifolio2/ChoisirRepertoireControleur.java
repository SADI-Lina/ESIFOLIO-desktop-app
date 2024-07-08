package com.example.esifolio2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ChoisirRepertoireControleur {
    @FXML private Hyperlink lien;
    @FXML private Label erreur;
    @FXML private Button boutonSelectionner;
    @FXML private Button boutonContinuer;
    @FXML private VBox vboxDirectoryChooser;

    @FXML public void authentifierClicked(ActionEvent actionEvent) throws IOException {
        /** Enregistrer le lien vers le répertoire **/
        if(EtatAppli.getLienRepertoire()==""){
            erreur.setText("Choisir répertoire");
        }
        else{
        /** Passer à la prochaine fenêtre **/
            erreur.setText("");
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("InfoPersoVue.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),900.0,600.0);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        window.centerOnScreen();
        }
    }

    @FXML public void authentifierMouseExited(MouseEvent mouseEvent) {
        boutonContinuer.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 8");
    }

    @FXML public void authentifierPressed(MouseEvent mouseEvent) {
        boutonContinuer.setStyle("-fx-background-color: #582B94; -fx-background-radius: 8");
    }

    @FXML public void authentifierSurvol(MouseEvent mouseEvent) {
        boutonContinuer.setStyle("-fx-background-color: #804BC6; -fx-background-radius: 8");
    }

    @FXML public void selectionnerClicked(ActionEvent actionEvent) {
        erreur.setText("");
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("src"));
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(window);
        EtatAppli.setLienRepertoire(selectedDirectory.getAbsolutePath());
        EtatAppli etat = new EtatAppli();
        etat.insertLienRepertoire();
        this.lien.setText(selectedDirectory.getAbsolutePath());
        this.lien.setOnAction(event -> {
            File file = new File(selectedDirectory.getAbsolutePath());
            try {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML public void selectionnerPressed(MouseEvent mouseEvent) {
        boutonSelectionner.setStyle("-fx-background-color: #582B94; -fx-background-radius: 8");
    }

    @FXML public void selectionnerMouseExited(MouseEvent mouseEvent) {
        boutonSelectionner.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 8");
    }

    @FXML public void selectionnerSurvol(MouseEvent mouseEvent) {
        boutonSelectionner.setStyle("-fx-background-color: #804BC6; -fx-background-radius: 8");
    }

    @FXML public void goToAide(){
        /** Ouvrir page HTML contenant l'aide **/
        File aide = new File("src/main/resources/com/example/esifolio2/Aide/index.html");
        ControleNavigateur.afficherPage(aide.getAbsolutePath());
    }
}
