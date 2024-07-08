package com.example.esifolio2;

import com.example.esifolio2.Authentification.AuthentificationModele;
import com.example.esifolio2.InfosPersonnelles.GestionDeFichiersModele;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class SaisirMDPControleur implements GestionDeFichiersModele {
    @FXML private Button bouttonAuthentification;
    @FXML private Label erreur1;
    @FXML private PasswordField motDePasse;
    @FXML private PasswordField motDePasseConfirme;

    @FXML public void authentifierClicked (ActionEvent actionEvent) throws IOException {
        if(!motDePasse.getText().trim().isEmpty() && !motDePasseConfirme.getText().trim().isEmpty()){
            if(!motDePasse.getText().equals(motDePasseConfirme.getText())){
                erreur1.setText("Mots de passe différents");
            }
            else{
                if(AuthentificationModele.validerMotDePasse(motDePasse.getText())==0){
                    erreur1.setText("Caractères alphanumériques uniquement");
                }
                else if(AuthentificationModele.validerMotDePasse(motDePasse.getText())==2){
                    erreur1.setText("Taille minimum : 6 caractères");
                }
                else{
                    erreur1.setText("");
                    /** Enregistrer mot de passe **/
                    EtatAppli.setMotDePasse(motDePasse.getText());
                    EtatAppli etat = new EtatAppli();
                    etat.insertMdp();
                    /** Passer à la prochaine page **/
                    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("ChoisirRepertoireVue.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(),900.0,600.0);
                    Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                    window.setScene(scene);
                    window.show();
                    window.centerOnScreen();
                }
            }
        }
        else{
            erreur1.setText("Champs obligatoires");
        }
    }

    @FXML public void authentifierMouseExited() {
        bouttonAuthentification.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 8");
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
