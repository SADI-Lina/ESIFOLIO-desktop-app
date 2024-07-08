package com.example.esifolio2;

import com.example.esifolio2.Authentification.AuthentificationModele;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;

public class AuthentificationControleur {
    @FXML private Button bouttonAuthentification;
    @FXML private Label erreur;
    @FXML private TextField champMail;

    static String mail;

    @FXML public void authentifierMouseExited() {
        bouttonAuthentification.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 8");
    }

    @FXML public void authentifierClicked(ActionEvent actionEvent) throws Exception {

        if((champMail.getText().trim().isEmpty())){
            erreur.setText("Champ obligatoire");
            champMail.setStyle("-fx-border-color: #FF0000; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else if((!AuthentificationModele.VerifierMail(champMail.getText())) ){
            erreur.setText("Adresse invalide");
            champMail.setStyle("-fx-border-color: #FF0000; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else{
            /** Générer un code aléatoire et l'envoyer par mail **/
            mail = champMail.getText();
            AuthentificationModele.sendMail(mail);
            /** Passer à la prochaine fenêtre **/
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("IntroductionCodeVue.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),900.0,600.0);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            window.centerOnScreen();
        }
    }

    @FXML public void authentifierSurvol() {
        bouttonAuthentification.setStyle("-fx-background-color: #804BC6; -fx-background-radius: 8");
    }

    @FXML public void authentifierPressed() {
        bouttonAuthentification.setStyle("-fx-background-color: #582B94; -fx-background-radius: 8");
    }

    @FXML void goToAide() {
        /** Ouvrir page HTML contenant l'aide **/
        File aide = new File("src/main/resources/com/example/esifolio2/Aide/index.html");
        ControleNavigateur.afficherPage(aide.getAbsolutePath());
    }
}
