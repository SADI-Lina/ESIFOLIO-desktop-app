package com.example.esifolio2;

import com.example.esifolio2.InfosPersonnelles.FichiersModele;
import com.example.esifolio2.InfosPersonnelles.GestionDeFichiersModele;
import com.example.esifolio2.Projet.ProjetEcole;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class CreerProjetEcoleControleur extends InteractionsControleur implements GestionDeFichiersModele {

    @FXML private Label erreur;
    @FXML private ComboBox <String> annee;
    @FXML private ComboBox <String> module;
    @FXML private Button bouttonSuivant;

    public void setAnnee(String annee){
        this.annee.setValue(annee);
    }

    public void setModule(){
        this.module.setDisable(false);
        ProjetEcole projet = new ProjetEcole();
        projet.setAnnee(this.annee.getValue());
        List<String> listeModules = projet.AfficherModules();
        this.module.getItems().setAll(listeModules);
        new RechercheComboBox(this.module);
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

    public void suivantClicked(ActionEvent actionEvent) throws IOException{

        /** Vérification module **/
        if(module.getValue()!=null) {
            erreur.setText("");
            /** Créer fichier intermédiaire **/
            File file = this.creer_f("DernierProjetEcole.folio");
            try {
                FileWriter writer = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(writer);
                bw.write("ANNEE " + annee.getValue());
                bw.newLine();
                bw.write("MODULE " + module.getValue());
                bw.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            /** Passer à la page suivante **/
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("CreerCompetencesEcoleVue.fxml"));
            Parent root = fxmlLoader.load();
            CreerCompetencesEcoleControleur controleur = fxmlLoader.getController();
            controleur.setNomPrenoms(info);
            ProjetEcole projetEcole = new ProjetEcole();
            projetEcole.setModule(module.getValue());
            projetEcole.setAnnee(annee.getValue());
            controleur.setCompetences(projetEcole.AfficherCompetences());
            Scene scene = new Scene(root, 900.0, 600.0);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            window.centerOnScreen();
        }
        else{
            erreur.setText("Sélectionnez l'année d'étude et le module");
        }
    }

    public void retourClicked(ActionEvent actionEvent) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("CreerProjetVue.fxml"));
        Parent root = fxmlLoader.load();
        CreerProjetControleur controleur = fxmlLoader.getController();
        String nom = FichiersModele.filtrer(Paths.get("src/ESIfolioapp/DernierProjetFirst.folio").toFile(),"NOM ");
        String type = FichiersModele.filtrer(Paths.get("src/ESIfolioapp/DernierProjetFirst.folio").toFile(),"TYPE ");
        String descriptif = FichiersModele.filtrer(Paths.get("src/ESIfolioapp/DernierProjetFirst.folio").toFile(),"DESCRIPTIF ");
        String lien = FichiersModele.filtrer(Paths.get("src/ESIfolioapp/DernierProjetFirst.folio").toFile(),"LIEN ");
        controleur.setNomProjet(nom);
        controleur.setDescriptif(descriptif);
        controleur.setType(type);
        if(!lien.equals("null")){
            controleur.setLien(lien);
        }
        controleur.setNomPrenoms(info);
        Scene scene = new Scene(root,900.0,600.0);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        window.centerOnScreen();
    }

    public void anneeClicked() {
            module.setDisable(false);
            ProjetEcole projet = new ProjetEcole();
            projet.setAnnee(annee.getValue());
            List<String> listeModules = projet.AfficherModules();
            module.getItems().setAll(listeModules);
            new RechercheComboBox(module);
    }
}
