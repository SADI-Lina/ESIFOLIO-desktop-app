package com.example.esifolio2;

import com.example.esifolio2.InfosPersonnelles.FichiersModele;
import com.example.esifolio2.InfosPersonnelles.GestionDeFichiersModele;
import com.example.esifolio2.Projet.Projet;
import com.example.esifolio2.Projet.ProjetClub;
import com.example.esifolio2.Projet.ProjetEcole;
import com.example.esifolio2.Projet.ProjetPersonnel;
import com.example.esifolio2.Statistiques.Statistique;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Paths;

public class InteractionsControleur implements GestionDeFichiersModele {
    @FXML protected Label nomPrenoms;

    protected File lienCompetences = Paths.get("src/Projet_Etudiant/Compétences_aquise.txt").toFile();
    protected File lienProjets = Paths.get("src/Projet_Etudiant/Fichier_nomsProjets.txt").toFile();
    protected String info = FichiersModele.filtrer(Paths.get("src/ESIfolioapp/InformationsPersonnelles.folio").toFile(),"NOM ")+" "+FichiersModele.filtrer(Paths.get("src/ESIfolioapp/InformationsPersonnelles.folio").toFile(),"PRENOMS ");
    protected Statistique statistique=new Statistique();

    public void setNomPrenoms(String nomPrenoms) {
        this.nomPrenoms.setText(nomPrenoms);
    }

    @FXML public void goToProjets(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("ProjetVue.fxml"));
        Parent root = fxmlLoader.load();
        ProjetControleur controleur = fxmlLoader.getController();
        controleur.setNomPrenoms(info);
        try {
            int ligneE = 1;
            int ligneC = 1;
            int ligneP = 1;
            BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(lienProjets), "UTF-8"));
            String line = read.readLine();
            while (line != null) {
                /** Custom Hbox **/
                HBox hbox = new HBox();
                hbox.setAlignment(Pos.CENTER_LEFT);
                hbox.setSpacing(20);
                hbox.setPadding(new Insets(0,50,0,50));
                hbox.setStyle("-fx-border-color: #C4C4C4 ; -fx-border-width: 1.5;");
                hbox.setPrefWidth(641);
                hbox.setPrefHeight(60);
                /** Custom Label et Vbox **/
                VBox vboxLabel = new VBox();
                vboxLabel.setAlignment(Pos.CENTER_LEFT);
                vboxLabel.setPrefWidth(446);
                Label projet = new Label();
                projet.setFont(new Font("Comfortaa Medium",16));
                /** Custom images pour bouttons **/
                FileInputStream inputAjouter = new FileInputStream(Paths.get("src/main/resources/com/example/esifolio2/images/Ajouter.png").toFile());
                FileInputStream inputSup = new FileInputStream(Paths.get("src/main/resources/com/example/esifolio2/images/Supprimer.png").toFile());
                Image imageAjouter = new Image(inputAjouter);
                ImageView imageViewAjouter = new ImageView(imageAjouter);
                Image imageSup = new Image(inputSup);
                ImageView imageViewSup = new ImageView(imageSup);
                /** Custom bouttons **/
                Button bouttonAjouter = new Button();
                Button bouttonSup = new Button();
                bouttonAjouter.setGraphic(imageViewAjouter);
                bouttonSup.setGraphic(imageViewSup);
                bouttonAjouter.setMaxSize(30,30);
                bouttonSup.setMaxSize(25,30);
                bouttonAjouter.setStyle("-fx-background-color: white; -fx-background-radius: 13;");
                bouttonSup.setStyle("-fx-background-color: white; -fx-background-radius: 6;");
                bouttonAjouter.setCursor(Cursor.HAND);
                bouttonSup.setCursor(Cursor.HAND);
                /** Remplir la grille **/
                if(line.contains("$E$")){
                    line=line.replace("$E$","");
                    File fichier = Paths.get("src/Projet_Etudiant/" + line + ".txt").toFile();
                    FileReader cont = new FileReader(fichier);
                    BufferedReader bw = new BufferedReader(cont);
                    String module = bw.readLine();
                    while(!module.equals("$M/moduleProjet/M$")){
                        module= bw.readLine();
                    }
                    module= bw.readLine();
                    bw.close();
                    projet.setText(line+" - "+module);
                    vboxLabel.getChildren().setAll(projet);
                    /** Boutton supprimer activé **/
                    String finalLine = line;
                    bouttonSup.setOnAction(actionEvent -> {
                        boolean reponse = FenetreSup.display();
                        if(reponse){
                            Projet.supprimerProjet(finalLine);
                        }
                    });
                    /** Boutton ajouter activé **/
                    String finalModule = module;
                    bouttonAjouter.setOnAction(actionEvent -> {
                        /** Enregistrer le nom du projet **/
                        File file = this.creer_f("DernierNomProjet.folio");
                        try {
                            FileWriter writer = new FileWriter(file);
                            BufferedWriter bwr = new BufferedWriter(writer);
                            bwr.write("NOM "+finalLine);
                            bwr.close();
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        /** Passer à la page **/
                        try {
                            FXMLLoader fxmlLoader1 = new FXMLLoader(this.getClass().getResource("AfficherProjetEcoleVue.fxml"));
                            Parent root1 = fxmlLoader1.load();
                            AfficherProjetEcoleControleur controleur1 = fxmlLoader1.getController();
                            controleur1.setNomPrenoms(info);
                            controleur1.setWrap();
                            controleur1.setModule(finalModule);
                            controleur1.setNom(finalLine);
                            ProjetEcole projetEcole = new ProjetEcole();
                            projetEcole.setModule(finalModule);
                            projetEcole.setAnnee(ProjetEcole.getAnnee(finalModule));
                            controleur1.setCompetencesListe(projetEcole.AfficherCompetences());
                            controleur1.setAttestation(Projet.getAttestation(finalLine));
                            controleur1.setCompetences(Projet.getCompetence(finalLine));
                            controleur1.setDescriptif(Projet.getDescriptif(finalLine));
                            Scene scene = new Scene(root1, 900.0, 600.0);
                            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                            window.setScene(scene);
                            window.show();
                            window.centerOnScreen();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    hbox.getChildren().addAll(vboxLabel,bouttonAjouter,bouttonSup);
                    controleur.setGridEcole(hbox,ligneE);
                    ligneE++;
                }
                else if(line.contains("$C$")){
                    line=line.replace("$C$","");
                    File fichier = Paths.get("src/Projet_Etudiant/" + line + ".txt").toFile();
                    FileReader cont = new FileReader(fichier);
                    BufferedReader bw = new BufferedReader(cont);
                    String lect = bw.readLine();
                    while(!lect.equals("$C/clubProjet/C$")){
                        lect= bw.readLine();
                    }
                    lect= bw.readLine();
                    bw.close();
                    projet.setText(line+" - "+lect);
                    vboxLabel.getChildren().setAll(projet);
                    /** Boutton supprimer activé **/
                    String finalLine = line;
                    bouttonSup.setOnAction(actionEvent -> {
                        boolean reponse = FenetreSup.display();
                        if(reponse){
                            Projet.supprimerProjet(finalLine);
                        }
                    });
                    /** Boutton ajouter activé **/
                    String finalLect = lect;
                    bouttonAjouter.setOnAction(actionEvent -> {
                        /** Enregistrer le nom du projet **/
                        File file = this.creer_f("DernierNomProjet.folio");
                        try {
                            FileWriter writer = new FileWriter(file);
                            BufferedWriter bwr = new BufferedWriter(writer);
                            bwr.write("NOM "+finalLine);
                            bwr.close();
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        /** Passer à la page **/
                        try {
                            FXMLLoader fxmlLoader1 = new FXMLLoader(this.getClass().getResource("AfficherProjetClubVue.fxml"));
                            Parent root1 = fxmlLoader1.load();
                            AfficherProjetClubControleur controleur1 = fxmlLoader1.getController();
                            controleur1.setNomPrenoms(info);
                            controleur1.setWrap();
                            ProjetClub projetClub = new ProjetClub();
                            projetClub.SetClub(finalLect);
                            controleur1.setClub(finalLect);
                            controleur1.setNom(finalLine);
                            controleur1.setAttestation(Projet.getAttestation(finalLine));
                            controleur1.setCompetence(Projet.getCompetence(finalLine),projetClub.AfficherCompetences());
                            controleur1.setDescriptif(Projet.getDescriptif(finalLine));
                            Scene scene = new Scene(root1, 900.0, 600.0);
                            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                            window.setScene(scene);
                            window.show();
                            window.centerOnScreen();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    hbox.getChildren().addAll(vboxLabel,bouttonAjouter,bouttonSup);
                    controleur.setGridClub(hbox,ligneC);
                    ligneC++;
                }
                else if(line.contains("$P$")){
                    line=line.replace("$P$","");
                    projet.setText(line);
                    vboxLabel.getChildren().setAll(projet);
                    /** Boutton supprimer activé **/
                    String finalLine = line;
                    bouttonSup.setOnAction(actionEvent -> {
                        boolean reponse = FenetreSup.display();
                        if(reponse){
                            Projet.supprimerProjet(finalLine);
                        }
                    });
                    /** Boutton ajouter activé **/
                    bouttonAjouter.setOnAction(actionEvent -> {
                        /** Enregistrer le nom du projet **/
                        File file = this.creer_f("DernierNomProjet.folio");
                        try {
                            FileWriter writer = new FileWriter(file);
                            BufferedWriter bwr = new BufferedWriter(writer);
                            bwr.write("NOM "+finalLine);
                            bwr.close();
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        /** Passer à la page **/
                        try {
                            FXMLLoader fxmlLoader1 = new FXMLLoader(this.getClass().getResource("AfficherProjetPersoVue.fxml"));
                            Parent root1 = fxmlLoader1.load();
                            AfficherProjetPersoControleur controleur1 = fxmlLoader1.getController();
                            controleur1.setNomPrenoms(info);
                            controleur1.setWrap();
                            ProjetPersonnel projetPersonnel = new ProjetPersonnel();
                            controleur1.setNom(finalLine);
                            controleur1.setAttestation(Projet.getAttestation(finalLine));
                            controleur1.setCompetence(Projet.getCompetence(finalLine),projetPersonnel.AfficherCompetences());
                            controleur1.setDescriptif(Projet.getDescriptif(finalLine));
                            Scene scene = new Scene(root1, 900.0, 600.0);
                            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                            window.setScene(scene);
                            window.show();
                            window.centerOnScreen();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    hbox.getChildren().addAll(vboxLabel,bouttonAjouter,bouttonSup);
                    controleur.setGridPerso(hbox,ligneP);
                    ligneP++;
                }
                line = read.readLine();
            }
            read.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root,900.0,600.0);
        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        window.centerOnScreen();
    }

    @FXML public void goToCompetences(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("CompetencesVue.fxml"));
        Parent root = fxmlLoader.load();
        CompetencesControleur controleur = fxmlLoader.getController();
        controleur.setNomPrenoms(info);
        try {
            int ligne = 1;
            BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(lienCompetences), "UTF-8"));
            String line = read.readLine();
            while (line != null) {
                HBox hbox = new HBox();
                Label competence = new Label();
                competence.setText(line);
                competence.setFont(new Font("Comfortaa Medium",15));
                competence.setWrapText(true);
                FileInputStream input = new FileInputStream(Paths.get("src/main/resources/com/example/esifolio2/images/Competences.png").toFile());
                Image image = new Image(input);
                ImageView imageView = new ImageView(image);
                hbox.getChildren().addAll(imageView,competence);
                hbox.setSpacing(20);
                hbox.setPadding(new Insets(10,7,10,12));
                hbox.setStyle("-fx-border-color: #C4C4C4 ; -fx-border-width: 1.5;");
                hbox.setPrefWidth(570);
                controleur.setGrid(hbox,ligne);
                line = read.readLine();
                ligne++;
            }
            read.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root,900.0,600.0);
        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        window.centerOnScreen();
    }

    @FXML public void goToPortfolio(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("PortfolioVue.fxml"));
        Parent root = fxmlLoader.load();
        PortfolioControleur controleur = fxmlLoader.getController();
        controleur.setNomPrenoms(info);
        Scene scene = new Scene(root,900.0,600.0);
        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        window.centerOnScreen();
    }

    @FXML public void goToStats(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("StatsVue.fxml"));
        statistique.setNbrCompétences(statistique.nombreCompetences(Paths.get("src/Projet_Etudiant/Compétences_aquise.txt").toFile()));
        Parent root = fxmlLoader.load();
        StatsControleur controleur = fxmlLoader.getController();
        controleur.setNomPrenoms(info);
        controleur.setNbProjets(statistique.NombrProjets(Paths.get("src/Projet_Etudiant").toFile()));
        controleur.setNbCompetences(statistique.nombreCompetences(Paths.get("src/Projet_Etudiant/Compétences_aquise.txt").toFile()));
        Scene scene = new Scene(root,900.0,600.0);
        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        window.centerOnScreen();
    }

    @FXML public void goToAccueil(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("AccueilVue.fxml"));
        Parent root = fxmlLoader.load();
        AccueilControleur controleur = fxmlLoader.getController();
        controleur.setNomPrenoms(info);
        Scene scene = new Scene(root,900.0,600.0);
        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        window.centerOnScreen();
    }

    @FXML public void goToContactezNous(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("ContactezNousVue.fxml"));
        Parent root = fxmlLoader.load();
        ContactezNousControleur controleur = fxmlLoader.getController();
        controleur.setNomPrenoms(info);
        controleur.setWrap();
        controleur.setNom(info);
        controleur.setMail(FichiersModele.filtrer(Paths.get("src/ESIfolioapp/InformationsPersonnelles.folio").toFile(),"MAIL "));
        Scene scene = new Scene(root,900.0,600.0);
        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        window.centerOnScreen();
    }

    @FXML public void goToModifierInfo(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("ModifierInfoVue.fxml"));
        Parent root = fxmlLoader.load();
        ModifierInfoControleur controleur = fxmlLoader.getController();
        controleur.setNom(FichiersModele.filtrer(Paths.get("src/ESIfolioapp/InformationsPersonnelles.folio").toFile(),"NOM "));
        controleur.setPrenoms(FichiersModele.filtrer(Paths.get("src/ESIfolioapp/InformationsPersonnelles.folio").toFile(),"PRENOMS "));
        controleur.setMail(FichiersModele.filtrer(Paths.get("src/ESIfolioapp/InformationsPersonnelles.folio").toFile(),"MAIL "));
        controleur.setDateNaissance(FichiersModele.filtrer(Paths.get("src/ESIfolioapp/InformationsPersonnelles.folio").toFile(),"DATE DE NAISSANCE "));
        controleur.setMatricule(FichiersModele.filtrer(Paths.get("src/ESIfolioapp/InformationsPersonnelles.folio").toFile(),"MATRICULE "));
        if(!FichiersModele.filtrer(Paths.get("src/ESIfolioapp/InformationsPersonnelles.folio").toFile(),"ADRESSE ").equals("null")){
            controleur.setAdresse(FichiersModele.filtrer(Paths.get("src/ESIfolioapp/InformationsPersonnelles.folio").toFile(),"ADRESSE "));
        }
        if(!FichiersModele.filtrer(Paths.get("src/ESIfolioapp/InformationsPersonnelles.folio").toFile(),"NUMERO DE TELEPHONE ").equals("null")){
            controleur.setNumTel(FichiersModele.filtrer(Paths.get("src/ESIfolioapp/InformationsPersonnelles.folio").toFile(),"NUMERO DE TELEPHONE "));
        }
        Scene scene = new Scene(root,900.0,600.0);
        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        window.centerOnScreen();
    }

    @FXML public void goToAide(MouseEvent mouseEvent) {
        /** Ouvrir page HTML contenant l'aide **/
        File aide = new File("src/main/resources/com/example/esifolio2/Aide/index.html");
        ControleNavigateur.afficherPage(aide.getAbsolutePath());
    }
}
