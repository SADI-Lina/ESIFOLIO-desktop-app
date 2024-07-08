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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

public class ModifierInfoControleur extends InteractionsControleur implements GestionDeFichiersModele {

    @FXML private Button boutonEnregistrer;
    @FXML private TextField nom;
    @FXML private TextField prenoms;
    @FXML private TextField adresseMail;
    @FXML private TextField matricule;
    @FXML private TextField dateNaissance;
    @FXML private TextField numTel;
    @FXML private TextField adresse;
    @FXML private Label modif;

    public void setNom(String nom){this.nom.setText(nom);}
    public void setPrenoms(String prenoms){this.prenoms.setText(prenoms);}
    public void setMail(String mail){this.adresseMail.setText(mail);}
    public void setMatricule(String matricule){this.matricule.setText(matricule);}
    public void setDateNaissance(String dateNaissance){this.dateNaissance.setText(dateNaissance);}
    public void setNumTel(String numTel){this.numTel.setText(numTel);}
    public void setAdresse(String adresse){this.adresse.setText(adresse);}

    private static Etudiant etudiant;

    @FXML public void enregistrerMouseExited() {
        boutonEnregistrer.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 7");
    }

    @FXML public void enregistrerPressed() {
        boutonEnregistrer.setStyle("-fx-background-color: #804BC6; -fx-background-radius: 7");
    }

    @FXML public void enregistrerSurvol() {
        boutonEnregistrer.setStyle("-fx-background-color: #582B94; -fx-background-radius: 7");
    }


    @FXML public void enregistrerClicked (ActionEvent actionEvent) throws IOException {

        etudiant = new Etudiant();
        boolean verifie1 = false;
        boolean verifie2 = false;
        boolean verifie3 = false;
        boolean verifie4 = false;
        boolean verifie5 = false;

        modif.setText("");
        /** Vérification nom **/

        if(nom.getText().trim().isEmpty()){
            nom.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else if(!InfoPersoControleur.verifierNom(nom.getText())){
            nom.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else if(!Character.isUpperCase(nom.getText().charAt(0))){
            nom.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else{
            verifie1 = true;
            nom.setStyle("-fx-border-color: #6F39B6; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
            etudiant.setNom(nom.getText());
        }

        /** Vérification prénoms **/

        if(prenoms.getText().trim().isEmpty()){
            prenoms.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else if(!InfoPersoControleur.verifierNom(nom.getText())){
            prenoms.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else if(!Character.isUpperCase(prenoms.getText().charAt(0))){
            prenoms.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else{
            verifie2 = true;
            prenoms.setStyle("-fx-border-color: #6F39B6; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
            etudiant.setPrenoms(prenoms.getText());
        }

        /** Vérification adresse mail **/

        if(adresseMail.getText().trim().isEmpty()){
            adresseMail.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else if(!AuthentificationModele.VerifierMail(adresseMail.getText())){
            adresseMail.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else{
            verifie3 = true;
            adresseMail.setStyle("-fx-border-color: #6F39B6; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
            etudiant.setAdresseMail(adresseMail.getText());
        }

        /** Vérification matricule **/

        if(matricule.getText().trim().isEmpty()) {
            matricule.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else if (!InfoPersoControleur.verifierMatricule(matricule.getText())){
            matricule.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else{
            verifie4 = true;
            matricule.setStyle("-fx-border-color: #6F39B6; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
            etudiant.setMatricule(matricule.getText());
        }

        /** Vérification date de naissance **/

        if(dateNaissance.getText().trim().isEmpty()){
            dateNaissance.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else if (!InfoPersoControleur.verifierDateDeNaissance(dateNaissance.getText())){
            dateNaissance.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else{
            verifie5 = true;
            dateNaissance.setStyle("-fx-border-color: #6F39B6; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
            etudiant.setDateNaissance(dateNaissance.getText());
        }

        /** Vérification numéro de téléphone **/

        if((!numTel.getText().trim().isEmpty())&&(InfoPersoControleur.verifierNumTel(numTel.getText()))){
            numTel.setStyle("-fx-border-color: #6F39B6; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
            etudiant.setNumTel(numTel.getText());
        }
        else if(!numTel.getText().trim().isEmpty()){
            numTel.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else if(numTel.getText().trim().isEmpty()){
            numTel.setStyle("-fx-border-color: #6F39B6; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }

        if(!adresse.getText().trim().isEmpty()){
            etudiant.setAdresse(adresse.getText());
        }
        if(verifie1 && verifie2 && verifie3 && verifie4 && verifie5){
            modif.setText("");
            /** Remplir le fichier contenant les informations personnelles de l'étudiant **/
            etudiant.insert();
            /** Passer à la prochaine page **/
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("ModifierInfoSuccesVue.fxml"));
            Parent root = fxmlLoader.load();
            ModifierInfoSuccesControleur controleur = fxmlLoader.getController();
            controleur.setNomPrenoms(info);
            Scene scene = new Scene(root, 900.0, 600.0);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            window.centerOnScreen();
        }
    }

}
