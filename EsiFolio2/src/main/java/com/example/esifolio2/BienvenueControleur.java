package com.example.esifolio2;

import com.example.esifolio2.InfosPersonnelles.FichiersModele;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

public class BienvenueControleur {

    public ImageView continuer;
    protected String info = FichiersModele.filtrer(Paths.get("src/ESIfolioapp/InformationsPersonnelles.folio").toFile(),"NOM ")+" "+FichiersModele.filtrer(Paths.get("src/ESIfolioapp/InformationsPersonnelles.folio").toFile(),"PRENOMS ");

    public void continuerClicked(MouseEvent mouseEvent) throws IOException {
       if(FichiersModele.filtrer(Paths.get("src/ESIfolioapp/PremiereConnexion.folio").toFile(),"PREMIERE CONNEXION ").equals("true")){
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
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("AuthentificationVue.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),900.0,600.0);
            Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            window.centerOnScreen();
        }

    }
}
