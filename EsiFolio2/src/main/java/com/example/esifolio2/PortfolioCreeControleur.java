package com.example.esifolio2;

import com.example.esifolio2.InfosPersonnelles.FichiersModele;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;

public class PortfolioCreeControleur extends InteractionsControleur{
    @FXML private Hyperlink chemin;
    @FXML private Label version;
    public void setChemin(int modele, String format){
        File fichier = new File("src/ESIfolioapp/LienRepertoire.folio");
        String nom = FichiersModele.filtrer(fichier,"LIEN REPERTOIRE ");
        this.chemin.setText(nom);
        this.chemin.setOnAction(actionEvent -> {
            File file = new File(nom);
            try {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.version.setText("Le modèle numéro " + modele + " sous format " + format + " a bien été créé!");
    }
}
