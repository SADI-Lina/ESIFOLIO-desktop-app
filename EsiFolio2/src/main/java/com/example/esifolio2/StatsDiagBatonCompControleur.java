package com.example.esifolio2;

import com.example.esifolio2.InfosPersonnelles.GestionDeFichiersModele;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class StatsDiagBatonCompControleur extends InteractionsControleur implements Initializable, GestionDeFichiersModele {
    @FXML private BarChart diagBatonComp;
    private File Comp = Paths.get("src/Statistiques/Compétences_aquise-types.txt").toFile();

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        XYChart.Series <String, Number> competences= new XYChart.Series();
        statistique.setNbrCompétences(statistique.nombreCompetences(Paths.get("src/Projet_Etudiant/Compétences_aquise.txt").toFile()));
        competences.getData().add(new XYChart.Data<String, Number>("Ecole", statistique.stat_type_Compétences(Comp)[0]));
        competences.getData().add(new XYChart.Data<String, Number>("Autre", statistique.stat_type_Compétences(Comp)[1]));
        diagBatonComp.setCategoryGap(190);
        diagBatonComp.getData().add(competences);
    }
}
