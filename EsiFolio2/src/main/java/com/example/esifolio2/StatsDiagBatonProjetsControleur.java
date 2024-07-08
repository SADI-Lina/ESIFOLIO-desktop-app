package com.example.esifolio2;

import com.example.esifolio2.InfosPersonnelles.GestionDeFichiersModele;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class StatsDiagBatonProjetsControleur extends InteractionsControleur implements Initializable, GestionDeFichiersModele {

    @FXML private BarChart diagBatonProjet;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        XYChart.Series <String, Number> projets= new XYChart.Series();
        statistique.setNbrProjet(statistique.NombrProjets(Paths.get("src/Projet_Etudiant").toFile()));
        projets.getData().add(new XYChart.Data<String, Number>("Ecole",statistique.stat_selon_type(Paths.get("src/Projet_Etudiant").toFile())[0]));
        projets.getData().add(new XYChart.Data<String, Number>("Club",statistique.stat_selon_type(Paths.get("src/Projet_Etudiant").toFile())[1]));
        projets.getData().add(new XYChart.Data<String, Number>("Personnels",statistique.stat_selon_type(Paths.get("src/Projet_Etudiant").toFile())[2]));
        //diagBatonProjet.setBarGap(50);
        diagBatonProjet.setCategoryGap(100);
        diagBatonProjet.getData().add(projets);
    }
}
