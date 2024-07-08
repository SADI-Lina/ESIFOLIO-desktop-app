package com.example.esifolio2;

import com.example.esifolio2.InfosPersonnelles.FichiersModele;
import com.example.esifolio2.InfosPersonnelles.GestionDeFichiersModele;
import com.example.esifolio2.Projet.Projet;
import com.example.esifolio2.Projet.ProjetClub;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AfficherProjetClubControleur extends InteractionsControleur implements GestionDeFichiersModele {
    @FXML private VBox box;
    @FXML private Button bouttonAjouter;
    @FXML private Button bouttonOuvrir;
    @FXML private TextField nom;
    @FXML private TextField club;
    @FXML private Label erreur;
    @FXML private TextField attestation;
    @FXML private TextArea descriptif;
    @FXML private ComboBox<String> competence1;
    @FXML private Button bouttonEnregistrer;

    public void setWrap(){
        descriptif.setWrapText(true);
    }

    public void setClub(String club){this.club.setText(club);}
    public void setNom (String nom){this.nom.setText(nom);}
    public void setAttestation (String attestation){this.attestation.setText(attestation);}
    public void setDescriptif (String descriptif){this.descriptif.setText(descriptif);}
    public void setCompetence(List<String> liste,List<String> listeComp){
        competence1.setValue(liste.get(0));
        competence1.getItems().setAll(listeComp);
        new RechercheComboBox(competence1);
        liste.remove(0);
        for(String element : liste){
            ComboBox<String> competence = this.ajouterClicked();
            competence.setValue(element);
            competence.getItems().setAll(listeComp);
            new RechercheComboBox(competence);
        }
    }

    public void enregistrerClicked(ActionEvent actionEvent) throws IOException {
        boolean verifi1 = false;
        boolean verifi2 = false;
        boolean verifi3 = false;
        boolean verifi4 = false;
        /** Initialisation de la pile **/
        Stack<ComboBox<String>> pile = new Stack<>();
        ObservableList<Node> observableList = box.getChildren();
        for(Node element : observableList){
            pile.push((ComboBox<String>) element);
            if(((ComboBox<String>) element).getValue()!=null && !((ComboBox<String>) element).getValue().equals("")){
                verifi1=true;
            }
        }
        /** Vérification nom **/
        if(this.nom.getText().trim().isEmpty()){
            erreur.setText("Nom obligatoire");
            nom.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else if(!Projet.VerifNomFich(this.nom.getText()) && this.nom.equals(FichiersModele.filtrer(Paths.get("src/ESIfolioapp/DernierNomProjet.folio").toFile(), "NOM "))){ //Vérifier si le nom n'existe pas pour un autre projet
            erreur.setText("Nom invalide");
            nom.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else{
            verifi2 = true;
            this.nom.setStyle("-fx-border-color: #6F39B6; -fx-background-color: #FAFAFA; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        /** Vérification descriptif **/
        if(this.descriptif.getText().trim().isEmpty()){
            erreur.setText("Descriptif obligatoire");
            this.descriptif.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        else {
            this.descriptif.setStyle("-fx-border-color: #6F39B6; -fx-background-color: #FAFAFA; -fx-border-width: 1.5; -fx-border-radius: 7");
            verifi3=true;
        }
        /** Vérification attestation **/
        if(this.attestation.getText()!=null && !this.attestation.getText().trim().isEmpty()) {
            if (!this.attestation.getText().contains("www")) {
                {
                    if(this.attestation.getText().equals(Projet.getAttestation(FichiersModele.filtrer(Paths.get("src/ESIfolioapp/DernierNomProjet.folio").toFile(), "NOM ")))){
                        verifi4 = true;
                        this.attestation.setStyle("-fx-border-color: #6F39B6; -fx-background-color: #FAFAFA; -fx-border-width: 1.5; -fx-border-radius: 7");
                    }
                    else if (Projet.verifierChemin(this.attestation.getText())) {
                        verifi4 = true;
                        this.attestation.setStyle("-fx-border-color: #6F39B6; -fx-background-color: #FAFAFA; -fx-border-width: 1.5; -fx-border-radius: 7");
                    } else {
                        erreur.setText("Lien ou chemin absolu invalide");
                        this.attestation.setStyle("-fx-border-color: RED; -fx-background-color: #FAFAFA; -fx-border-width: 1.5; -fx-border-radius: 7");
                    }
                }
            }
            else{
                verifi4 = true;
                this.attestation.setStyle("-fx-border-color: #6F39B6; -fx-background-color: #FAFAFA; -fx-border-width: 1.5; -fx-border-radius: 7");
            }
        }
        else{
            verifi4 = true;
            erreur.setText("");
            this.attestation.setStyle("-fx-border-color: #6F39B6; -fx-background-color: #FAFAFA; -fx-border-width: 1.5; -fx-border-radius: 7");
        }
        if(verifi1 && verifi2 && verifi3 && verifi4) {
            /** Nouvelles données **/
            String newnom = this.nom.getText();
            String newdescriptif = this.descriptif.getText();
            String newattestation = this.attestation.getText();
            /** Enregistrer les nouvelles compétences dans un fichier **/
            List<String>comp = new ArrayList<>();
            File fich = this.creer_f("DernierProjetCompetences.folio");
            Stack<ComboBox<String>> newpile = new Stack<>();
            try {
                FileWriter writer = new FileWriter(fich);
                BufferedWriter bw = new BufferedWriter(writer);
                while(!pile.empty()){
                    if(pile.peek().getValue() != null && !pile.peek().getValue().equals("")){
                        bw.write(pile.peek().getValue());
                        bw.newLine();
                        comp.add(pile.peek().getValue());
                    }
                    newpile.push(pile.pop());
                }
                bw.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            /** Anciennes données **/
            String nom = FichiersModele.filtrer(Paths.get("src/ESIfolioapp/DernierNomProjet.folio").toFile(), "NOM ");
            String descriptif = Projet.getDescriptif(nom);
            String oldAttestation = Projet.getAttestation(nom);
            /** Modifications **/
            if (!nom.equals(newnom)) Projet.ModifInfo(nom, "nom", newnom);
            if (!descriptif.equals(newdescriptif)) Projet.ModifInfo(newnom, "descriptif", newdescriptif);
            if(this.attestation.getText()!=null && !this.attestation.getText().equals(oldAttestation)) Projet.ModifInfo(newnom, "attestation", newattestation);
            else if(this.attestation.getText()==null){
                Projet.ModifInfo(newnom, "attestation", "$$");
            }
            List<String> newComp = new ArrayList<>();
            while(!newpile.empty()){
                if(newpile.peek().getValue()!=null && !newpile.peek().getValue().equals("")){
                    newComp.add(newpile.peek().getValue());
                }
                newpile.pop();
            }
            List<String> oldComp = Projet.getCompetence(newnom);
            for(String elemento : oldComp){
                Projet.SuppressionInfo(Paths.get("src/Projet_Etudiant/" + newnom + ".txt").toFile(),elemento);
            }
            for(String elementn : newComp){
                Projet.ajoutInfo(Paths.get("src/Projet_Etudiant/" + newnom + ".txt").toFile(),elementn);
            }
            /** Passer à la prochaine page **/
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("ModificationsEnregistreesVue.fxml"));
            Parent root = fxmlLoader.load();
            ModificationsEnregistreesControleur controleur = fxmlLoader.getController();
            controleur.setNomPrenoms(info);
            Scene scene = new Scene(root, 900.0, 600.0);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            window.centerOnScreen();
        }
        /** Vérification compétences **/
        if(!verifi1){
            erreur.setText("Minimum une compétence");
        }
    }

    public void enregistrerMouseExited(MouseEvent mouseEvent) {
        bouttonEnregistrer.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 10");
    }

    public void enregistrerPressed(MouseEvent mouseEvent) {
        bouttonEnregistrer.setStyle("-fx-background-color: #582B94; -fx-background-radius: 10");
    }

    public void enregistrerSurvol(MouseEvent mouseEvent) {
        bouttonEnregistrer.setStyle("-fx-background-color: #804BC6; -fx-background-radius: 10");
    }

    public ComboBox<String> ajouterClicked() {
        List<String> liste = ProjetClub.AffichageCompetences();
        ComboBox<String> competence = new ComboBox<>();
        String css = this.getClass().getResource("combobox.css").toExternalForm();
        competence.getStylesheets().add(css);
        competence.setPromptText("Compétence");
        competence.getItems().setAll(liste);
        new RechercheComboBox(competence);
        box.getChildren().add(competence);
        return competence;
    }

    public void ajouterMouseExited(MouseEvent mouseEvent) {
        bouttonAjouter.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 10");
    }

    public void ajouterPressed(MouseEvent mouseEvent) {
        bouttonAjouter.setStyle("-fx-background-color: #582B94; -fx-background-radius: 10");
    }

    public void ajouterSurvol(MouseEvent mouseEvent) {
        bouttonAjouter.setStyle("-fx-background-color: #804BC6; -fx-background-radius: 10");
    }


    public void ouvrirClicked() throws IOException {
        if(!attestation.getText().trim().isEmpty()){
            if(attestation.getText().contains("http")){
                ControleNavigateur.afficherPage(attestation.getText());
            }
            else {
                File fichier = new File(attestation.getText());
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + fichier);
            }
        }
    }

    public void ouvrirMouseExited() {
        bouttonOuvrir.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 10");
    }

    public void ouvrirPressed() {
        bouttonOuvrir.setStyle("-fx-background-color: #582B94; -fx-background-radius: 10");
    }

    public void ouvrirSurvol() {
        bouttonOuvrir.setStyle("-fx-background-color: #804BC6; -fx-background-radius: 10");
    }
}
