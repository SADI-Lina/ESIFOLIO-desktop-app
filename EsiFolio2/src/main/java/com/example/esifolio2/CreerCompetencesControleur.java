package com.example.esifolio2;

import com.example.esifolio2.InfosPersonnelles.FichiersModele;
import com.example.esifolio2.InfosPersonnelles.GestionDeFichiersModele;
import com.example.esifolio2.Projet.Projet;
import com.example.esifolio2.Projet.ProjetClub;
import com.example.esifolio2.Projet.ProjetPersonnel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CreerCompetencesControleur extends InteractionsControleur implements GestionDeFichiersModele {

    @FXML private VBox box;
    @FXML private Button bouttonAjouter;
    @FXML private Label erreur;
    @FXML private ComboBox<String> competence1;
    @FXML private Button bouttonCreer;

    public void setCompetences(List<String> liste){
        competence1.getItems().setAll(liste);
        new RechercheComboBox(competence1);
    }

    @FXML public void creerMouseExited() {
        bouttonCreer.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 10");
    }

    @FXML public void creerPressed() {
        bouttonCreer.setStyle("-fx-background-color: #582B94; -fx-background-radius: 10");
    }

    @FXML public void creerSurvol() {
        bouttonCreer.setStyle("-fx-background-color: #804BC6; -fx-background-radius: 10");
    }

    public void creerClicked(ActionEvent actionEvent) throws IOException {
        boolean cree1 = false;
        boolean cree3 = false;
        if(competence1.getValue()==null || competence1.getValue().equals("")){
            erreur.setText("Minimum une compétence");
        }
        else {
            File file = this.creer_f("DernierProjetCompetences.folio");
            File projet = new File("src/");
            List<String> comp = new ArrayList<>();
            /** Remplir le tableau de compétences **/
            ObservableList<Node> combos = box.getChildren();
            for(Node element : combos){
                if(((ComboBox<String>)element).getValue()!=null && !((ComboBox<String>)element).getValue().equals("")){
                    comp.add(((ComboBox<String>)element).getValue());
                }
            }
            try {
                FileWriter writer = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(writer);
                for(String elements : comp){
                    bw.write(elements);
                    bw.newLine();
                }
                bw.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String nom = FichiersModele.filtrer(Paths.get("src/ESIfolioapp/DernierProjetFirst.folio").toFile(),"NOM ");
            String descriptif = FichiersModele.filtrer(Paths.get("src/ESIfolioapp/DernierProjetFirst.folio").toFile(),"DESCRIPTIF ");
            String attestation = null;
            if(!FichiersModele.filtrer(Paths.get("src/ESIfolioapp/DernierProjetFirst.folio").toFile(),"CHEMIN ").equals("null")){
                attestation = FichiersModele.filtrer(Paths.get("src/ESIfolioapp/DernierProjetFirst.folio").toFile(),"CHEMIN ");
            }
            else if(!FichiersModele.filtrer(Paths.get("src/ESIfolioapp/DernierProjetFirst.folio").toFile(),"LIEN ").equals("null")){
                attestation = FichiersModele.filtrer(Paths.get("src/ESIfolioapp/DernierProjetFirst.folio").toFile(),"LIEN ");
            }
            /** Création du projet **/
                if (FichiersModele.filtrer(Paths.get("src/ESIfolioapp/DernierProjetFirst.folio").toFile(), "TYPE ").equals("Projet personnel")) {
                    ProjetPersonnel projetPersonnel = new ProjetPersonnel(nom, "Personnel", descriptif, projet, file, attestation);
                    File prjet = projetPersonnel.creerProjet(comp);
                    projetPersonnel.SetFichierPrjt(prjet);
                    File fichcmp = projetPersonnel.RemFichComp(comp);
                    List<String> tab2 = projetPersonnel.TabComp1(comp);
                    Projet.RemFichComp1(tab2);
                    projetPersonnel.SetFichierComp(fichcmp);
                    projetPersonnel.listFichiers(nom);
                    cree1 = true;
                }else if (FichiersModele.filtrer(Paths.get("src/ESIfolioapp/DernierProjetFirst.folio").toFile(), "TYPE ").equals("Projet de club")) {
                    String club = FichiersModele.filtrer(Paths.get("src/ESIfolioapp/DernierProjetClub.folio").toFile(), "CLUB ");
                    ProjetClub projetClub = new ProjetClub(nom, "Club", descriptif, projet, file, attestation, club);
                    File prjet = projetClub.creerProjet(comp);
                    projetClub.SetFichierPrjt(prjet);
                    projetClub.RemFichComp(comp);
                    List<String> tab2 = projetClub.TabComp1(comp);
                    Projet.RemFichComp1(tab2);
                    projetClub.listFichiers(nom);
                    cree3 = true;
                }
                /** Passer à la page succès **/
                if (cree1 || cree3) {
                    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("CreerSuccesVue.fxml"));
                    Parent root = fxmlLoader.load();
                    CreerSuccesControleur controleur = fxmlLoader.getController();
                    controleur.setNomPrenoms(info);
                    Scene scene = new Scene(root, 900.0, 600.0);
                    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    window.setScene(scene);
                    window.show();
                    window.centerOnScreen();
                }
        }
    }

    public void retourClicked(ActionEvent actionEvent) throws IOException {
        if(FichiersModele.filtrer(Paths.get("src/ESIfolioapp/DernierProjetFirst.folio").toFile(),"TYPE ").equals("Projet personnel")){
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
        else if(FichiersModele.filtrer(Paths.get("src/ESIfolioapp/DernierProjetFirst.folio").toFile(),"TYPE ").equals("Projet de club")){
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("CreerProjetClubVue.fxml"));
            Parent root = fxmlLoader.load();
            CreerProjetClubControleur controleur = fxmlLoader.getController();
            String club = FichiersModele.filtrer(Paths.get("src/ESIfolioapp/DernierProjetClub.folio").toFile(),"CLUB ");
            controleur.setClub(club);
            controleur.setClub();
            controleur.setNomPrenoms(info);
            Scene scene = new Scene(root,900.0,600.0);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            window.centerOnScreen();
        }
    }

    public void ajouterClicked() {
        List<String> liste = ProjetClub.AffichageCompetences();
        ComboBox<String> competence = new ComboBox<>();
        String css = this.getClass().getResource("combobox.css").toExternalForm();
        competence.getStylesheets().add(css);
        competence.setPromptText("Compétence");
        competence.getItems().setAll(liste);
        new RechercheComboBox(competence);
        box.getChildren().add(competence);
    }

    public void ajouterMouseExited() {
        bouttonAjouter.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 10");
    }

    public void ajouterPressed() {
        bouttonAjouter.setStyle("-fx-background-color: #582B94; -fx-background-radius: 10");
    }

    public void ajouterSurvol() {
        bouttonAjouter.setStyle("-fx-background-color: #804BC6; -fx-background-radius: 10");
    }
}
