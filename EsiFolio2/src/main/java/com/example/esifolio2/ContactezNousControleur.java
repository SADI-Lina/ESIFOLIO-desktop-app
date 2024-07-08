package com.example.esifolio2;

import com.example.esifolio2.Authentification.AuthentificationModele;
import com.example.esifolio2.InfosPersonnelles.GestionDeFichiersModele;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ContactezNousControleur extends InteractionsControleur implements GestionDeFichiersModele {
    @FXML private TextField mail;
    @FXML private TextField nom;
    @FXML private Button boutonEnvoyer;
    @FXML private TextArea message;

    public void setWrap(){
        this.message.setWrapText(true);
    }

    public void setNomPrenoms(String nomPrenoms) {
        this.nomPrenoms.setText(nomPrenoms);
    }
    public void setNom(String nom){this.nom.setText(nom);}
    public void setMail(String mail){this.mail.setText(mail);}


    public void envoyerClicked(ActionEvent actionEvent) throws Exception {
        /** Envoyer le mail **/
        if(!message.getText().trim().isEmpty()){
            AuthentificationModele.sendMailContact(message.getText(),mail.getText(),nom.getText());
            /** Passer à la prochaine page **/
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("MailEnvoyeVue.fxml"));
            Parent root = fxmlLoader.load();
            MailEnvoyeControleur controleur = fxmlLoader.getController();
            controleur.setNomPrenoms(info);
            Scene scene = new Scene(root,900.0,600.0);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            window.centerOnScreen();
        }
    }

    public void envoyerSurvol() {
        boutonEnvoyer.setStyle("-fx-background-color: #804BC6; -fx-background-radius: 8");
    }

    public void envoyerMouseExited() {
        boutonEnvoyer.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 8");
    }

    public void envoyerPressed() {
        boutonEnvoyer.setStyle("-fx-background-color: #582B94; -fx-background-radius: 8");
    }
}
