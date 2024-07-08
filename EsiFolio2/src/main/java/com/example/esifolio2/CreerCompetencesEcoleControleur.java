package com.example.esifolio2;

import com.example.esifolio2.InfosPersonnelles.FichiersModele;
import com.example.esifolio2.InfosPersonnelles.GestionDeFichiersModele;
import com.example.esifolio2.Projet.Projet;
import com.example.esifolio2.Projet.ProjetEcole;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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

public class CreerCompetencesEcoleControleur extends InteractionsControleur implements GestionDeFichiersModele {
    @FXML private VBox box;
    @FXML private Button bouttonCreer;

    private Stack <CheckBox> pile;

    public void setCompetences(List<String> liste){
        this.pile = new Stack<>();
        for(String element : liste){
            CheckBox checkBox = new CheckBox(element);
            checkBox.setWrapText(true);
            String css = this.getClass().getResource("checkbox.css").toExternalForm();
            checkBox.getStylesheets().add(css);
            this.pile.push(checkBox);
        }
        box.getChildren().addAll(this.pile);
    }

    public void retourClicked(ActionEvent actionEvent) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("CreerProjetEcoleVue.fxml"));
            Parent root = fxmlLoader.load();
            CreerProjetEcoleControleur controleur = fxmlLoader.getController();
            String annee = FichiersModele.filtrer(Paths.get("src/ESIfolioapp/DernierProjetEcole.folio").toFile(),"ANNEE ");
            controleur.setAnnee(annee);
            controleur.setModule();
            controleur.setNomPrenoms(info);
            Scene scene = new Scene(root,900.0,600.0);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            window.centerOnScreen();
    }

    public void creerClicked(ActionEvent actionEvent) throws IOException {
        boolean cree = false;
            File file = this.creer_f("DernierProjetCompetences.folio");
            File projet = new File("src/");
            ArrayList<String> listeComp = new ArrayList<>();
            try {
                FileWriter writer = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(writer);
                while(!pile.empty()){
                    if(pile.peek().isSelected()){
                        bw.write(pile.peek().getText());
                        bw.newLine();
                        listeComp.add(pile.peek().getText());
                    }
                    pile.pop();
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
                String annee = FichiersModele.filtrer(Paths.get("src/ESIfolioapp/DernierProjetEcole.folio").toFile(), "ANNEE ");
                String module = FichiersModele.filtrer(Paths.get("src/ESIfolioapp/DernierProjetEcole.folio").toFile(), "MODULE ");
                System.out.println();
                ProjetEcole projetEcole = new ProjetEcole(nom, "Ecole", descriptif, projet, file, attestation, module, annee);
                File prjet = projetEcole.creerProjet(listeComp);
                projetEcole.SetFichierPrjt(prjet);
                File fichComp = projetEcole.RemFichComp(listeComp);
                List<String> tab2 = projetEcole.TabComp1(listeComp);
                Projet.RemFichComp1(tab2);
                projetEcole.SetFichierComp(fichComp);
                projetEcole.listFichiers(nom);
                cree = true;
            /** Passer à la page succès **/
            if (cree) {
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

    @FXML public void creerMouseExited() {
        bouttonCreer.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 10");
    }

    @FXML public void creerPressed() {
        bouttonCreer.setStyle("-fx-background-color: #582B94; -fx-background-radius: 10");
    }

    @FXML public void creerSurvol() {
        bouttonCreer.setStyle("-fx-background-color: #804BC6; -fx-background-radius: 10");
    }
}
