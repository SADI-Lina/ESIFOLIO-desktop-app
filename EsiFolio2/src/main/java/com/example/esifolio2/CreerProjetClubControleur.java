package com.example.esifolio2;

import com.example.esifolio2.InfosPersonnelles.FichiersModele;
import com.example.esifolio2.InfosPersonnelles.GestionDeFichiersModele;
import com.example.esifolio2.Projet.ProjetClub;
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

public class CreerProjetClubControleur extends InteractionsControleur implements GestionDeFichiersModele {

    @FXML private Label erreur;
    @FXML private ComboBox<String> club;
    @FXML private Button bouttonSuivant;

    public void setClub(String club){
        this.club.setValue(club);
    }

    public ComboBox getClub(){
        return this.club;
    }

    public void setClub(){
        List<String> listeClub = ProjetClub.AffichageClub();
        this.club.getItems().addAll(listeClub);
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

        /** Vérification club **/
        if(club.getValue()!=null) {
            erreur.setText("");
            /** Créer fichier intermédiaire **/
            File file = this.creer_f("DernierProjetClub.folio");
            try {
                FileWriter writer = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(writer);
                bw.write("CLUB " + club.getValue());
                bw.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            /** Passer à la page suivante **/
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("CreerCompetencesVue.fxml"));
            Parent root = fxmlLoader.load();
            CreerCompetencesControleur controleur = fxmlLoader.getController();
            controleur.setNomPrenoms(info);
            controleur.setCompetences(new ProjetClub().AfficherCompetences());
            Scene scene = new Scene(root, 900.0, 600.0);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            window.centerOnScreen();
        }
        else{
            erreur.setText("Sélectionnez le club");
        }
    }

    public void retourClicked(ActionEvent actionEvent) throws IOException {
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

}
