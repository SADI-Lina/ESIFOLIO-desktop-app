package com.example.esifolio2;

import com.example.esifolio2.InfosPersonnelles.GestionDeFichiersModele;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ProjetControleur extends InteractionsControleur implements GestionDeFichiersModele {

    @FXML private Button bouttonCreer;
    @FXML private GridPane gridClub;
    @FXML private GridPane gridEcole;
    @FXML private GridPane gridPerso;

    public void setGridEcole(Node node,int ligne) {
        this.gridEcole.setStyle("-fx-background-color : white");
        this.gridEcole.add(node,0,ligne);
        this.gridEcole.setMinSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE);
        this.gridEcole.setPrefSize(641,Region.USE_COMPUTED_SIZE);
        this.gridEcole.setMaxSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE);
    }

    public void setGridClub(Node node,int ligne) {
        this.gridClub.setStyle("-fx-background-color : white");
        this.gridClub.add(node,0,ligne);
        this.gridClub.setMinSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE);
        this.gridClub.setPrefSize(641,Region.USE_COMPUTED_SIZE);
        this.gridClub.setMaxSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE);
    }

    public void setGridPerso(Node node,int ligne) {
        this.gridPerso.setStyle("-fx-background-color : white");
        this.gridPerso.add(node,0,ligne);
        this.gridPerso.setMinSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE);
        this.gridPerso.setPrefSize(641,Region.USE_COMPUTED_SIZE);
        this.gridPerso.setMaxSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE);
    }

    @FXML void creerClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("CreerProjetVue.fxml"));
        Parent root = fxmlLoader.load();
        CreerProjetControleur controleur = fxmlLoader.getController();
        controleur.setNomPrenoms(info);
        controleur.setWrap();
        File file2 = this.creer_f("DernierProjet.folio");
        try {
            FileWriter writer = new FileWriter(file2);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write("NOM null" );
            bw.newLine();
            bw.write("TYPE null" );
            bw.newLine();
            bw.write("DESCRIPTIF null" );
            bw.newLine();
            bw.write("CHEMIN null" );
            bw.newLine();
            bw.write("MODULE null" );
            bw.newLine();
            bw.write("CLUB null" );
            bw.newLine();
            bw.write("COMPETENCES null" );
            bw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = this.creer_f("CheminDernierProjet.folio");
        try {
            FileWriter writer = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write("CHEMIN null");
            bw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file3 = this.creer_f("DernierProjetEcole.folio");
        try {
            FileWriter writer = new FileWriter(file3);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write("ANNEE null");
            bw.newLine();
            bw.write("MODULE null");
            bw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file4 = this.creer_f("DernierProjetClub.folio");
        try {
            FileWriter writer = new FileWriter(file4);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write("CLUB null");
            bw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root,900.0,600.0);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        window.centerOnScreen();
    }

    @FXML void creerMouseExited() {
        bouttonCreer.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 12");
    }

    @FXML void creerPressed() {
        bouttonCreer.setStyle("-fx-background-color: #582B94; -fx-background-radius: 12");
    }

    @FXML void creerSurvol() {
        bouttonCreer.setStyle("-fx-background-color: #804BC6; -fx-background-radius: 12");
    }

    public void actualiserClicked(MouseEvent mouseEvent) throws IOException {
        goToProjets(mouseEvent);
    }
}
