package com.example.esifolio2;

import com.example.esifolio2.InfosPersonnelles.GestionDeFichiersModele;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class CompetencesControleur extends InteractionsControleur implements GestionDeFichiersModele {
    @FXML private GridPane grid;

    public void setGrid(Node node,int ligne) {
        this.grid.add(node,0,ligne);
        this.grid.setMinSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE);
        this.grid.setPrefSize(585,Region.USE_COMPUTED_SIZE);
        this.grid.setMaxSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE);
    }
}
