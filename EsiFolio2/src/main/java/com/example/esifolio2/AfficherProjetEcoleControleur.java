package com.example.esifolio2;

import com.example.esifolio2.InfosPersonnelles.FichiersModele;
import com.example.esifolio2.InfosPersonnelles.GestionDeFichiersModele;
import com.example.esifolio2.Projet.Projet;
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

public class AfficherProjetEcoleControleur extends InteractionsControleur implements GestionDeFichiersModele {
    @FXML private VBox box;
    @FXML private Label erreur;
    @FXML private TextField nom;
    @FXML private TextField module;
    @FXML private TextField attestation;
    @FXML private TextArea descriptif;
    @FXML private Button bouttonEnregistrer;
    @FXML private Button bouttonOuvrir;

    private Stack<CheckBox> pile;

    public void setWrap(){
        descriptif.setWrapText(true);
    }

    public void setModule (String module){this.module.setText(module);}
    public void setNom (String nom){this.nom.setText(nom);}
    public void setAttestation (String attestation){this.attestation.setText(attestation);}
    public void setDescriptif (String descriptif){this.descriptif.setText(descriptif);}
    public void setCompetencesListe(List<String> liste){
        this.pile = new Stack<>();
        if(!liste.isEmpty()) {
            for (String element : liste) {
                CheckBox checkBox = new CheckBox(element);
                checkBox.setWrapText(true);
                String css = this.getClass().getResource("checkbox.css").toExternalForm();
                checkBox.getStylesheets().add(css);
                this.pile.push(checkBox);
            }
            box.getChildren().addAll(this.pile);
        }
    }
    public void setCompetences(List <String> list){
        if(pile!=null) {
            while (!pile.empty()) {
                if (list.contains(pile.peek().getText())) {
                    pile.peek().setSelected(true);
                }
                pile.pop();
            }
        }
    }
    public void enregistrerClicked(ActionEvent actionEvent) throws IOException {
        boolean verifi1 = false;
        boolean verifi2 = false;
        boolean verifi3 = false;
        boolean verifi4 = false;
        /** Initialisation de la pile **/
        Stack<CheckBox> pile = new Stack<>();
        ObservableList<Node> observableList = box.getChildren();
        for(Node element : observableList){
            pile.push((CheckBox) element);
            if(((CheckBox) element).isSelected()){
                verifi1=true;
            }
        }
        if(observableList.isEmpty()){
            verifi1=true;
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
            List<String> comp = new ArrayList<>();
            File fich = this.creer_f("DernierProjetCompetences.folio");
            Stack<CheckBox> newpile = new Stack<>();
            try {
                FileWriter writer = new FileWriter(fich);
                BufferedWriter bw = new BufferedWriter(writer);
                while(!pile.empty()){
                    if(pile.peek().isSelected()){
                        bw.write(pile.peek().getText());
                        bw.newLine();
                        comp.add(pile.peek().getText());
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
            while(!newpile.empty()){
                if(!newpile.peek().isSelected()){
                    Projet.SuppressionInfo(Paths.get("src/Projet_Etudiant/" + newnom + ".txt").toFile(),newpile.peek().getText());
                }
                else{
                    Projet.ajoutInfo(Paths.get("src/Projet_Etudiant/" + newnom + ".txt").toFile(),newpile.peek().getText());
                }
                newpile.pop();
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
