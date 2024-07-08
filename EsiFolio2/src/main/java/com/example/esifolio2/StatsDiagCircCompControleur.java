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

public class StatsDiagCircCompControleur extends InteractionsControleur implements Initializable, GestionDeFichiersModele {

    @FXML private PieChart diagCircComp;
    public void initialize (URL url, ResourceBundle resourceBundle){
        ObservableList<PieChart.Data> pieChart = FXCollections.observableArrayList(
                new PieChart.Data("Ecole",statistique.stat_type_Compétences(Paths.get("src/Statistiques/Compétences_aquise-types.txt").toFile())[0]),
                new PieChart.Data("Autres",statistique.stat_type_Compétences(Paths.get("src/Statistiques/Compétences_aquise-types.txt").toFile())[1])
        );

        pieChart.forEach(data -> data.nameProperty().bind(Bindings.concat(data.getName(), " : ", data.pieValueProperty())));
        diagCircComp.getData().addAll(pieChart);
    }
}
