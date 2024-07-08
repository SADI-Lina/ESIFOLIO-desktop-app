package com.example.esifolio2;

import com.example.esifolio2.InfosPersonnelles.GestionDeFichiersModele;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class StatsDiagCircProjetsControleur extends InteractionsControleur implements Initializable, GestionDeFichiersModele {

    @FXML private PieChart diagCircProjets;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        ObservableList<PieChart.Data> pieChart = FXCollections.observableArrayList(
                new PieChart.Data("Ecole",statistique.stat_selon_type(Paths.get("src/Projet_Etudiant").toFile())[0]),
                new PieChart.Data("Club",statistique.stat_selon_type(Paths.get("src/Projet_Etudiant").toFile())[1]),
                new PieChart.Data("Personnel",statistique.stat_selon_type(Paths.get("src/Projet_Etudiant").toFile())[2])
        );

        pieChart.forEach(data -> data.nameProperty().bind(Bindings.concat(data.getName(), " : ", data.pieValueProperty())));
        diagCircProjets.getData().addAll(pieChart);
    }
}
