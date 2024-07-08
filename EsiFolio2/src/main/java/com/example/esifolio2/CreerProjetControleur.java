package com.example.esifolio2;

import com.example.esifolio2.InfosPersonnelles.FichiersModele;
import com.example.esifolio2.InfosPersonnelles.GestionDeFichiersModele;
import com.example.esifolio2.Projet.Projet;
import com.example.esifolio2.Projet.ProjetPersonnel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Paths;

public class CreerProjetControleur extends InteractionsControleur implements GestionDeFichiersModele {
    @FXML private Label erreurAttestation;
    @FXML private Label erreurDescriptif;
    @FXML private Label erreurNom;
    @FXML private Label erreurType;

    @FXML private TextField attestation;
    @FXML private Button bouttonSuivant;
    @FXML private TextArea descriptif;
    @FXML private ComboBox <String> type;
    @FXML private TextField nom;

    public void setWrap(){
        descriptif.setWrapText(true);
    }

    public void setLien(String lien){
        this.attestation.setText(lien);
    }

    public void setDescriptif(String descriptif){
        this.descriptif.setText(descriptif);
    }

    public void setType(String type){
        this.type.setValue(type);
    }

    public void setNomProjet(String nomProjet){
        nom.setText(nomProjet);
    }

    public void suivantClicked(ActionEvent actionEvent) throws IOException{
        boolean verifi1 = false;
        boolean verifi2 = false;
        boolean verifi3 = false;
        String chemin = "null";
        String lien = "null";

        /** Vérification type **/
        if(type.getValue()!=null) {
            verifi1=true;
            type.setStyle("-fx-border-color: #6F39B6");
            erreurType.setText("");
        }
        else{
            type.setStyle("-fx-border-color: RED");
            erreurType.setText("Type non choisi");
        }

        /** Vérification nom **/
        if(nom.getText().trim().isEmpty()){
            erreurNom.setText("Nom obligatoire");
            nom.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else if(!Projet.VerifNomFich(nom.getText())){ //Vérifier si le nom n'existe pas pour un autre projet
            erreurNom.setText("Nom invalide");
            nom.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else{
            verifi2=true;
            erreurNom.setText("");
            nom.setStyle("-fx-border-color: #6F39B6; -fx-background-color: #FAFAFA; -fx-border-width: 1.5; -fx-border-radius: 7");
        }

        /** Vérification descriptif **/
        if(descriptif.getText().trim().isEmpty()){
            erreurDescriptif.setText("Descriptif obligatoire");
            descriptif.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else {
            descriptif.setStyle("-fx-border-color: #6F39B6; -fx-background-color: #FAFAFA; -fx-border-width: 1.5; -fx-border-radius: 7");
            verifi3=true;
            erreurDescriptif.setText("");
        }

        /** Vérification attestation **/
        if(!attestation.getText().trim().isEmpty() && !attestation.getText().contains("www")){
            erreurAttestation.setText("Lien invalide");
            attestation.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else {
            if(!FichiersModele.filtrer(Paths.get("src/ESIfolioapp/CheminDernierProjet.folio").toFile(),"CHEMIN ").equals("null")){
                chemin = FichiersModele.filtrer(Paths.get("src/ESIfolioapp/CheminDernierProjet.folio").toFile(),"CHEMIN ");
            }
            else if (!attestation.getText().trim().isEmpty()){
                lien = attestation.getText();
            }
            attestation.setStyle("-fx-border-color: #6F39B6; -fx-background-color: #FAFAFA; -fx-border-width: 1.5; -fx-border-radius: 7");
            erreurAttestation.setText("");
        }
        if(verifi1 && verifi2 && verifi3){
            /** Enregistrer les données dans un fichier de passage **/
            File file2 = this.creer_f("DernierProjetFirst.folio");
            try {
                FileWriter writer = new FileWriter(file2);
                BufferedWriter bw = new BufferedWriter(writer);
                bw.write("NOM " + nom.getText());
                bw.newLine();
                bw.write("TYPE " + type.getValue() );
                bw.newLine();
                bw.write("DESCRIPTIF " + descriptif.getText() );
                bw.newLine();
                bw.write("CHEMIN " + chemin );
                bw.newLine();
                bw.write("LIEN " + lien );
                bw.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (type.getValue().equals("Projet de l'école")) {
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("CreerProjetEcoleVue.fxml"));
                Parent root = fxmlLoader.load();
                CreerProjetEcoleControleur controleur = fxmlLoader.getController();
                controleur.setNomPrenoms(info);
                Scene scene = new Scene(root,900.0,600.0);
                Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
                window.centerOnScreen();
            } else if (type.getValue().equals("Projet de club")) {
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("CreerProjetClubVue.fxml"));
                Parent root = fxmlLoader.load();
                CreerProjetClubControleur controleur = fxmlLoader.getController();
                controleur.setNomPrenoms(info);
                controleur.setClub();
                new RechercheComboBox(controleur.getClub());
                Scene scene = new Scene(root,900.0,600.0);
                Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
                window.centerOnScreen();
            } else if (type.getValue().equals("Projet personnel")) {
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("CreerCompetencesVue.fxml"));
                Parent root = fxmlLoader.load();
                CreerCompetencesControleur controleur = fxmlLoader.getController();
                controleur.setNomPrenoms(info);
                controleur.setCompetences(new ProjetPersonnel().AfficherCompetences());
                Scene scene = new Scene(root,900.0,600.0);
                Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
                window.centerOnScreen();
            }
        }
    }

    public void joindreAttestation(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image ou PDF","*.png","*.jpg","*.pdf"));
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(window);
        if(selectedFile!=null) {
            /** Créer nouveau fichier **/
            File file = this.creer_f("CheminDernierProjet.folio");
            try {
                FileWriter writer = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(writer);
                bw.write("CHEMIN " + String.valueOf(selectedFile.getAbsolutePath()));
                bw.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void suivantMouseExited() {
        bouttonSuivant.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 10");
    }

    public void suivantPressed() {
        bouttonSuivant.setStyle("-fx-background-color: #582B94; -fx-background-radius: 10");
    }

    public void suivantSurvol() {
        bouttonSuivant.setStyle("-fx-background-color: #804BC6; -fx-background-radius: 10");
    }
}
