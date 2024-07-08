package com.example.esifolio2;

import com.example.esifolio2.Authentification.AuthentificationModele;
import com.example.esifolio2.InfosPersonnelles.FichiersModele;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class IntroduireCodeControleur {
    @FXML private Button boutonRenvoyer;
    @FXML private TextField codeSecret;
    @FXML private Button boutonContinuer;
    @FXML private Label erreur;

    String code;

    @FXML public void authentifierClicked(ActionEvent actionEvent) throws IOException {
        if (codeSecret.getText().trim().isEmpty()){
            erreur.setText("Champ obligatoire");
            codeSecret.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else{
            /** Verification que c'est le même code que celui qu'on a envoyé **/
            if(FichiersModele.filtrer(Paths.get("src/ESIfolioapp/CodeConfirmation.folio").toFile(),"CODE ").compareTo(codeSecret.getText())==0){
                erreur.setText("");
                codeSecret.setStyle("-fx-border-color: #6F39B6; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
                /** Passer à la prochaine page **/
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("SaisieMDPVue.fxml"));
                Scene scene = new Scene(fxmlLoader.load(),900.0,600.0);
                Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
                window.centerOnScreen();
            }
            else{
                erreur.setText("Code invalide");
                codeSecret.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
            }
        }
    }

    @FXML public void authentifierMouseExited(MouseEvent mouseEvent) {
        boutonContinuer.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 7");
    }

    @FXML public void authentifierPressed(MouseEvent mouseEvent) {
        boutonContinuer.setStyle("-fx-background-color: #804BC6; -fx-background-radius: 7");
    }

    @FXML public void authentifierSurvol(MouseEvent mouseEvent) {
        boutonContinuer.setStyle("-fx-background-color: #582B94; -fx-background-radius: 7");
    }

    @FXML public void renvoyerClicked(ActionEvent actionEvent) throws Exception {
        /** Générer un nouveau code et l'envoyer par mail **/
        AuthentificationModele.sendMail(AuthentificationControleur.mail);
    }

    @FXML public void renvoyerMouseExited(MouseEvent mouseEvent) {
        boutonRenvoyer.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 7");
    }

    @FXML public void renvoyerPressed(MouseEvent mouseEvent) {
        boutonRenvoyer.setStyle("-fx-background-color: #804BC6; -fx-background-radius: 7");
    }

    @FXML public void renvoyerSurvol(MouseEvent mouseEvent) {
        boutonRenvoyer.setStyle("-fx-background-color: #582B94; -fx-background-radius: 7");
    }

    @FXML void goToAide() {
        /** Ouvrir page HTML contenant l'aide **/
        File aide = new File("src/main/resources/com/example/esifolio2/Aide/index.html");
        ControleNavigateur.afficherPage(aide.getAbsolutePath());
    }
}
