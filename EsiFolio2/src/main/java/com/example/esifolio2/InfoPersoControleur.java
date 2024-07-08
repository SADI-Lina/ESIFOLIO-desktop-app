package com.example.esifolio2;

import com.example.esifolio2.Authentification.AuthentificationModele;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.regex.Pattern;

public class InfoPersoControleur {


    @FXML private Button bouttonContinuer;

    @FXML private TextField nom;
    @FXML private TextField prenoms;
    @FXML private TextField adresseMail;
    @FXML private TextField matricule;
    @FXML private TextField dateNaissance;
    @FXML private TextField numTel;
    @FXML private TextField adresse;

    @FXML private Label erreurNom;
    @FXML private Label erreurPrenoms;
    @FXML private Label erreurAdresseMail;
    @FXML private Label erreurMatricule;
    @FXML private Label erreurDateNaissance;
    @FXML private Label erreurNumTel;

    private static Etudiant etudiant;

    public static Etudiant getEtudiant() {
        return etudiant;
    }

    @FXML
    public void continuerClicked (ActionEvent actionEvent) throws IOException{

        etudiant = new Etudiant();
        boolean verifie1 = false;
        boolean verifie2 = false;
        boolean verifie3 = false;
        boolean verifie4 = false;
        boolean verifie5 = false;

        /** Vérification nom **/

        if(nom.getText().trim().isEmpty()){
            erreurNom.setText("Champ obligatoire");
            nom.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else if(!InfoPersoControleur.verifierNom(nom.getText())){
            erreurNom.setText("Nom composé uniquement de lettres");
            nom.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else if(!Character.isUpperCase(nom.getText().charAt(0))){
            erreurNom.setText("Majuscule obligatoire pour la première lettre");
            nom.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else{
            verifie1 = true;
            erreurNom.setText("");
            nom.setStyle("-fx-border-color: #6F39B6; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
            etudiant.setNom(nom.getText());
        }

        /** Vérification prénoms **/

        if(prenoms.getText().trim().isEmpty()){
            erreurPrenoms.setText("Champ obligatoire");
            prenoms.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else if(!InfoPersoControleur.verifierNom(prenoms.getText()) ){
            erreurPrenoms.setText("Prénoms composés uniquement de lettres");
            prenoms.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else if(!Character.isUpperCase(prenoms.getText().charAt(0))){
            erreurPrenoms.setText("Majuscule obligatoire pour la première lettre");
            prenoms.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else{
            erreurPrenoms.setText("");
            verifie2 = true;
            prenoms.setStyle("-fx-border-color: #6F39B6; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
            etudiant.setPrenoms(prenoms.getText());
        }

        /** Vérification adresse mail **/

        if(adresseMail.getText().trim().isEmpty()){
            erreurAdresseMail.setText("Champ obligatoire");
            adresseMail.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else if(!AuthentificationModele.VerifierMail(adresseMail.getText())){
            erreurAdresseMail.setText("Adresse invalide");
            adresseMail.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else{
            verifie3 = true;
            erreurAdresseMail.setText("");
            adresseMail.setStyle("-fx-border-color: #6F39B6; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
            etudiant.setAdresseMail(adresseMail.getText());
        }

        /** Vérification matricule **/

        if(matricule.getText().trim().isEmpty()) {
            erreurMatricule.setText("Champ obligatoire");
            matricule.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else if (!InfoPersoControleur.verifierMatricule(matricule.getText())){
            erreurMatricule.setText("Format invalide");
            matricule.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else{
            verifie4 = true;
            erreurMatricule.setText("");
            matricule.setStyle("-fx-border-color: #6F39B6; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
            etudiant.setMatricule(matricule.getText());
        }

        /** Vérification date de naissance **/

        if(dateNaissance.getText().trim().isEmpty()){
            erreurDateNaissance.setText("Champ obligatoire");
            dateNaissance.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else if (!InfoPersoControleur.verifierDateDeNaissance(dateNaissance.getText())){
            erreurDateNaissance.setText("Format invalide");
            dateNaissance.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else{
            verifie5 = true;
            erreurDateNaissance.setText("");
            dateNaissance.setStyle("-fx-border-color: #6F39B6; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
            etudiant.setDateNaissance(dateNaissance.getText());
        }

        /** Vérification numéro de téléphone **/

        if((!numTel.getText().trim().isEmpty())&&(InfoPersoControleur.verifierNumTel(numTel.getText()))){
            erreurNumTel.setText("");
            numTel.setStyle("-fx-border-color: #6F39B6; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
            etudiant.setNumTel(numTel.getText());
        }
        else if(!numTel.getText().trim().isEmpty()){
            erreurNumTel.setText("Format invalide");
            numTel.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else if(numTel.getText().trim().isEmpty()){
            erreurNumTel.setText("");
            numTel.setStyle("-fx-border-color: #6F39B6; -fx-background-color: #FAFAFA ; -fx-border-width: 1.5; -fx-border-radius: 7");
        }

        if(!adresse.getText().trim().isEmpty()){
            etudiant.setAdresse(adresse.getText());
        }
        if(verifie1 && verifie2 && verifie3 && verifie4 && verifie5){
            /** Remplir le fichier contenant les informations personnelles de l'étudiant **/
            etudiant.insert();
            //TemplateControleur.setNomPrenoms(nom.getText() + prenoms.getText());
            /** Remplir le fichier première connexion **/
            EtatAppli.setPremiereConnexion(true);
            EtatAppli etat = new EtatAppli();
            etat.insertPremiereCo();
            /** Passer à la nouvelle fenêtre **/
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("AccueilVue.fxml"));
            Parent root = fxmlLoader.load();
            AccueilControleur controleur = fxmlLoader.getController();
            controleur.setNomPrenoms(nom.getText()+" "+prenoms.getText());
            Scene scene = new Scene(root,900.0,600.0);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            window.centerOnScreen();
        }
    }

    @FXML public void continuerSurvol(MouseEvent mouseEvent) {
        bouttonContinuer.setStyle("-fx-background-color: #804BC6; -fx-background-radius: 12");
    }

    @FXML public void continuerPressed(MouseEvent mouseEvent) {
        bouttonContinuer.setStyle("-fx-background-color: #582B94; -fx-background-radius: 12");
    }

    @FXML public void continuerMouseExited(MouseEvent mouseEvent) {
        bouttonContinuer.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 12");
    }

    public static boolean verifierMatricule(String matricule){
        if(matricule.indexOf("/")==2){
            try{
                int mat = Integer.parseInt(matricule.replace("/",""));
                return true;
            }
            catch (NumberFormatException e){
                return false;
            }
        }
        else{
            return false;
        }
    }

    public static boolean verifierDateDeNaissance(String dateNaissance){
        if(dateNaissance.indexOf("/")==2){
            dateNaissance=(dateNaissance.replaceFirst("/",""));
            if(dateNaissance.indexOf("/")==4) {
                try {
                    int date = Integer.parseInt(dateNaissance.replaceFirst("/", ""));
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    public static boolean verifierNumTel(String numTel){
        if(numTel.indexOf("0")==0) {
            try {
                int num = Integer.parseInt(numTel);
                if(numTel.length()==10){
                    return true;
                }
                else{
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        else{
            return false;
        }
    }

    public static boolean verifierNom(String nom){
        int i = 0;
        boolean verifie = true;
         char lettre, lettreMin;
        while(i<nom.length() && verifie){
            lettre = nom.charAt(i);
            if((int) lettre <97 && (int) lettre > 122){
                if((int) lettre <65 && (int) lettre > 90){
                    if(lettre != ' '){
                        verifie = false;
                    }
                }
            }
            i++;
        }
        return verifie;
    }

    public void goToAide() {
        /** Ouvrir page HTML contenant l'aide **/
        File aide = new File("src/main/resources/com/example/esifolio2/Aide/index.html");
        ControleNavigateur.afficherPage(aide.getAbsolutePath());
    }
}
